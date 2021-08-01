/*
 * Copyright (c) 2021 XPATH-QS
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

package org.xpathqs.core.selector.extensions

import org.xpathqs.core.selector.NullSelector
import org.xpathqs.core.selector.base.BaseSelector
import org.xpathqs.core.selector.base.ISelector

/**
 * returns selector's name without parent name
 */
val <T : BaseSelector> T.simpleName: String
    get() {
        return name.substringAfterLast(".")
    }

/**
 * returns selector's root parent
 *
 * Require #1 - When Selector doesn't have base, [NullSelector] should be returned
 * @sample org.xpathqs.core.reflection.extensions.RootParentTest.r1_rootParent
 *
 * Require #2 - When Selector has one level base, that base should be returned
 * @sample org.xpathqs.core.reflection.extensions.RootParentTest.r2_rootParent
 *
 * Require #3 - When Selector has two and more level base, the latest should be returned
 * @sample org.xpathqs.core.reflection.extensions.RootParentTest.r3_rootParent
 */
val <T : BaseSelector> T.rootParent: ISelector
    get() {
        var parent = this.base as? BaseSelector
        while (parent != null) {
            if(parent.base !is NullSelector) {
                parent = parent.base as? BaseSelector
            } else {
                break
            }
        }
        return parent ?: NullSelector()
    }


/**
 * @return all [BaseSelector]'s parents
 *
 * Require #1 - when selector doesn't have any parent then empty list should be returned
 * @sample [org.xpathqs.core.selector.base.extension.ParentsTest.r1_parents]
 *
 * Require #2 - when selector have one parent, it should be returned
 * @sample [org.xpathqs.core.selector.base.extension.ParentsTest.r2_parents]
 *
 * Require #3 - when selector have more than one parent, they all should be returned
 * @sample [org.xpathqs.core.selector.base.extension.ParentsTest.r3_parents]
 */
val BaseSelector.parents: Collection<BaseSelector>
    get() {
        val res = ArrayList<BaseSelector>()

        var base = this.base
        while (base is BaseSelector) {
            res.add(base)
            base = base.base
        }

        return res
    }