/*
 * Copyright (c) 2022 XPATH-QS
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

package org.xpathqs.core.reflection

import org.xpathqs.core.selector.NullSelector
import org.xpathqs.core.selector.base.BaseSelector
import org.xpathqs.core.selector.base.BaseSelectorProps
import org.xpathqs.core.selector.base.ISelector
import org.xpathqs.core.selector.block.Block
import org.xpathqs.core.selector.group.GroupSelector
import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import kotlin.reflect.full.declaredMembers
import kotlin.reflect.full.superclasses
import kotlin.reflect.jvm.javaField

/**
 * Set of functional extensions to manipulate with <pre>selectors</pre> via Reflection
 */

/**
 * Check if <pre>Any</pre> is an instance of an <pre>object-class</pre>
 * @sample org.xpathqs.core.reflection.extensions.ReflectionExtensionsIsObjectTests
 */
internal fun Any.isObject(): Boolean {
    if (this is Class<*>) {
        return this.declaredFields
            .find {
                it.name == "INSTANCE"
            } != null
    } else if(this is KClass<*>) {
        return this.objectInstance != null
    }
    return this::class.objectInstance != null
}

/**
 * Check if object is a subtype of [Block]
 *
 * Requirements:
 * #1 - true when object is a subtype of [Block] but not Block directly
 * @sample [org.xpathqs.core.reflection.extensions.ReflectionExtensionsIsBlockSubtypeTests.r1]
 * #2 - false for all others cases
 */
internal fun Any.isBlockSubtype(): Boolean {
    return this is Block && this.javaClass.superclass.simpleName != "Block"
}
/**
 * Get an Object instance of [Block] when class is an Object-class inherited from block
 * @sample org.xpathqs.core.reflection.extensions.ReflectionExtensionsGetObjectTests
 */
internal fun KClass<*>.getObject(): Block {
    return (objectInstance as? Block) ?: throw IllegalArgumentException(
        "Provided class $simpleName is not inherited from the Block class"
    )
}

/**
 * Check class for having [ISelector] as an inherited parent
 */
@Suppress("ReturnCount")
internal fun KClass<*>.isSelectorSubtype(): Boolean {
    if (this == BaseSelector::class.java
        || this == NullSelector::class.java
        || this == ISelector::class.java) {
        return true
    }
    if (this.supertypes.isEmpty()) {
        return false
    }
    return ISelector::class.java.isAssignableFrom(this.superclasses.first().java)
            || this.java.isAssignableFrom(ISelector::class.java)
}

/**
 * Sets the <pre>name</pre> to the [BaseSelector] via reflection
 */
internal fun <T : BaseSelector> T.setName(name: String): T {
    SelectorReflection(this)
        .setName(name)
    return this
}

/**
 * Sets the <pre>base</pre> to the [BaseSelector] via reflection
 */
internal fun <T : BaseSelector> T.setBase(base: ISelector): T {
    SelectorReflection(this)
        .setBase(base)
    return this
}

/**
 * Sets the <pre>setNoBase</pre> to the [BaseSelector] via reflection
 */
internal fun <T : BaseSelector> T.setNoBase(value: Boolean): T {
    SelectorReflection(this)
        .setProp(this::noBase.name, value)
    return this
}

/**
 * Sets the <pre>props</pre> to the [BaseSelector] via reflection
 */
internal fun <T : BaseSelector> T.setProps(props: BaseSelectorProps): T {
    SelectorReflection(this)
        .setProps(props)
    return this
}

/**
 * Freeze [BaseSelector] which means that <pre>clone</pre> method
 * will return a new instance
 */
fun <T : BaseSelector> T.freeze(): T {
    SelectorReflection(this).freeze()
    return this
}

/**
 * Freeze [GroupSelector] which means that <pre>clone</pre> method
 * will return a new instance
 */
fun <T : GroupSelector> T.freeze(): T {
    SelectorReflection(this).freeze()
    this.selectorsChain.forEach {
        it.freeze()
    }
    return this
}

/**
 * Freeze [Block] which means that <pre>clone</pre> method
 * will return a new instance. And all inner selectors should be frozen as well
 */
fun <T : Block> T.freeze(): T {
    (this as GroupSelector).freeze()

    this.children.forEach {
        it.freeze()
    }
    return this
}

/**
 * Mark [BaseSelector] as <pre>cloned</pre>
 * will return this same instance from the <pre>clone</pre> method
 */
internal fun <T : BaseSelector> T.cloned(): T {
    SelectorReflection(this).cloned()
    return this
}

/**
 * Sets the [Block.isBlank] property via reflection
 */
internal fun Block.setBlank(value: Boolean) {
    SelectorReflection(this).setProp("isBlank", value)
}

/**
 * Converts Kotlin Reflection property to the Java Reflection field
 */
internal fun KProperty<*>.toField() = this.javaField!!.apply {
    isAccessible = true
}


/**
 * Check if the provided object is a member of an inner class
 *
 * Require #1 - false for any objects instead of inner class members
 * @sample org.xpathqs.core.reflection.extensions.ReflectionExtensionsTest.isInnerClassForSelector
 *
 * Require #2 - true for the inner class member
 * @sample org.xpathqs.core.reflection.extensions.ReflectionExtensionsTest.isInnerClassForInnerClassMember
 */
internal fun Any.isInnerClass(): Boolean {
    return getInnerClassMember() != null
}

/**
 * Return member of inner class
 */
internal fun Any.getInnerClassMember(): Any? {
    val f = this::class.java.declaredFields.find {
        it.name.contains("this$")
    }
    f?.isAccessible = true
    return f?.get(this)
}

/**
 * Sets the <pre>base</pre> to the [BaseSelector] via reflection
 */
internal fun <T : BaseSelector> T.setAnnotations(annotations: Collection<Annotation>): T {
    SelectorReflection(this)
        .setAnnotations(annotations)
    return this
}

/**
 * Sets field
 */
internal fun <T : BaseSelector> T.setProperty(prop: KProperty<*>?): T {
    SelectorReflection(this)
        .setProperty(prop)
    return this
}

/**
 * Parse [Block].
 * @see SelectorParser.parse
 */
internal fun <T : Block> T.parse(): T {
    SelectorParser(this).parse()
    return this
}