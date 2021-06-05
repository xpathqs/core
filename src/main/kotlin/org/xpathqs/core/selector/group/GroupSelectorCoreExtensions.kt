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

package org.xpathqs.core.selector.group

import org.xpathqs.core.selector.base.BaseSelector
import org.xpathqs.core.selector.base.deepClone
import org.xpathqs.core.selector.extensions.clone

/**
 * @return clone of the [GroupSelector] object with all of it's properties
 *
 * Require #1 - [BaseSelector.deepClone] should be invoked first of all
 *              to instantiate an object and init all of it's inherited properties
 * @sample org.xpathqs.core.selector.group.extensions.DeepCloneTest.r1_parentPropsInit
 *
 * Require #2 - all selectors from [GroupSelector.selectorsChain] should be cloned
 * @sample org.xpathqs.core.selector.group.extensions.DeepCloneTest.r2_selectorsChain
 *
 * Require #3 - xpath's of base and cloned objects should be equal
 * @sample org.xpathqs.core.selector.group.extensions.DeepCloneTest.r3_xpathEquals
 *
 * Require #4 - modification of the cloned object should not have any effect to the base object
 * @sample org.xpathqs.core.selector.group.extensions.DeepCloneTest.r4_clonedModification
 */
@Suppress("UNCHECKED_CAST")
internal fun <T : GroupSelector> T.deepClone(): T {
    val cloned = (this as BaseSelector).deepClone() as T
    cloned.selectorsChain = ArrayList<BaseSelector>().apply {
        addAll(
            this@deepClone.selectorsChain.map {
                it.clone()
            }
        )
    }
    return cloned
}
