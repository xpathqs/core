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

package org.xpathqs.core.selector.args

import org.xpathqs.core.selector.base.ISelector
import org.xpathqs.core.selector.selector.Selector
import org.xpathqs.core.selector.selector.prefix

enum class InnerSelectorArg {
    ROOT, ALL, NO
}

/**
 * Wraps selector into the ValueArg object
 *
 * @REQUIREMENTS:
 * #1 - by default, selector's prefix should be used
 * @sample org.xpathqs.core.selector.args.SelectorArgTest.r1
 *
 * #2 - the 'ALL' type should modify selector's prefix by adding the "any" child semantics
 * @sample org.xpathqs.core.selector.args.SelectorArgTest.r2
 *
 * #3 - the 'ROOT' type should modify selector's prefix by adding the "from the root" child semantics
 * @sample org.xpathqs.core.selector.args.SelectorArgTest.r3
 *
 * #4 - if [selector] is not an instance of [Selector], it's prefix should not be changed
 * @sample org.xpathqs.core.selector.args.SelectorArgTest.r4
 *
 * #5 - if [selector] has an axe it should not be removed
 * @sample org.xpathqs.core.selector.args.SelectorArgTest.r5
 */
class SelectorArg(
    private val selector: ISelector,
    joinType: JoinType = JoinType.NONE,
    private val type: InnerSelectorArg = InnerSelectorArg.NO,
): ValueArg(joinType = joinType) {
    override val value: String
        get() {
            return if(selector is Selector) {
                selector
                when (type) {
                    InnerSelectorArg.ALL -> {
                        selector.prefix(".//").toXpath()
                    }
                    InnerSelectorArg.ROOT -> {
                        selector.prefix("./").toXpath()
                    }
                    else -> {
                        selector.xpath
                    }
                }
            } else {
                selector.xpath
            }
        }
}