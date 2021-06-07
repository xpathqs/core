/*
 * Copyright (c) 2021 Nikita A. Chegodaev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.xpathqs.core.selector.base

import org.xpathqs.core.reflection.*
import org.xpathqs.core.selector.block.Block
import java.lang.reflect.Constructor

/**
 * @return an ability of [Block] object to be cloned
 *
 * Can't clone selector if it's parent (member holder)
 * is an object-class.
 *
 * @require - #1 return false for the object-class member
 * @sample [org.xpathqs.core.selector.base.extension.CanBeClonedTest.r1_canBeClonedForObject]
 *
 * @require - #2 return true for the standard member
 * @sample [org.xpathqs.core.selector.base.extension.CanBeClonedTest.r2_canBeClonedForMember]
 */
fun <T : BaseSelector> T.canBeCloned(): Boolean = !this.base.isObject()

/**
 * @return clone of the [BaseSelector] object with all of it's properties
 *
 * @require - #1 Selectors without [SelectorState.FREEZE] should not be cloned
 * @sample org.xpathqs.core.selector.base.extension.DeepCloneTest.r1_deepCloneForInitState
 *
 * @require - #2 Freeze-Selectors should be cloned with all of their properties
 * @sample org.xpathqs.core.selector.base.extension.DeepCloneTest.r2_deepCloneForFreezeSelector
 *
 * @require - #3 Cloned-Selector should have a [SelectorState.CLONED] state
 * @sample org.xpathqs.core.selector.base.extension.DeepCloneTest.r3_deepCloneShouldChangeState
 */
fun <T : BaseSelector> T.deepClone(): T {
    if (this.state != SelectorState.FREEZE) {
        return this
    }

    val newObj = this.newInstance()

    newObj.setName(this.name)
    newObj.setBase(this.base)
    newObj.setAnnotations(this.annotations)
    newObj.setField(this.field)
    newObj.setProps(this.props.clone())

    newObj.cloned()

    return newObj
}

/**
 * @return new instance of object class with the default properties
 *
 * REQUIREMENTS FOR SINGLE SELECTORS:
 *
 * Require #1 - ability to instantiate a [org.xpathqs.core.selector.selector.Selector] class
 * @sample [org.xpathqs.core.selector.base.extension.NewInstanceTest.r1_newInstanceForSelector]
 *
 * Require #2 - ability to instantiate a [org.xpathqs.core.selector.xpath.XpathSelector] class
 * @sample [org.xpathqs.core.selector.base.extension.NewInstanceTest.r2_newInstanceForXpathSelector]
 *
 * Require #3 - ability to instantiate a [org.xpathqs.core.selector.group.GroupSelector] class
 * @sample org.xpathqs.core.selector.base.extension.NewInstanceTest.r3_newInstanceForGroupSelector
 *
 * REQUIREMENTS FOR [Block] SELECTORS
 *
 * Require #4 - ability to instantiate a [Block] class with selectors
 * @sample org.xpathqs.core.selector.base.extension.NewInstanceTest.r4_newInstanceForBlockSelector
 *
 * Require #5 - ability to instantiate a [Block] class with [Block] class members
 * @sample org.xpathqs.core.selector.base.extension.NewInstanceTest.r5_newInstanceForBlockSelectorWithBlockMembers
 *
 * Require #6 - ability to instantiate a [Block] class with [Block] Inner classes members
 * @sample org.xpathqs.core.selector.base.extension.NewInstanceTest.r6_newInstanceForBlockSelectorWithBlockInnerMembers
 *
 * Require #7 - ability to instantiate a [Block] class-object
 * @sample org.xpathqs.core.selector.base.extension.NewInstanceTest.r7_newInstanceForBlockObject
 */
@Suppress("UNCHECKED_CAST")
internal fun <T : BaseSelector> T.newInstance(): T {
    val res = if (this.isInnerClass()) {
        val c = this.getConstructor(1)
        c.newInstance(this.getInnerClassMember())
    } else {
        val c = this.getConstructor(0)
        c.newInstance()
    }

    return res as T
}

/**
 * @return constructor with provided parameterCount
 */
private fun Any.getConstructor(parameterCount: Int): Constructor<*> {
    val c = this::class.java.declaredConstructors.find {
        it.parameterCount == parameterCount
    } ?: throw IllegalArgumentException("Selector doesn't have a default constructor with $parameterCount parameter")

    c.isAccessible = true
    return c
}