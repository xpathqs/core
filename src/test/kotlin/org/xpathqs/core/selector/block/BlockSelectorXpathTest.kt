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

package org.xpathqs.core.selector.block

import org.junit.jupiter.api.Test
import org.xpathqs.core.reflection.parse
import org.xpathqs.core.selector.extensions.core.get
import org.xpathqs.core.util.SelectorFactory.tagSelector
import org.xpathqs.gwt.GIVEN

class BlockSelectorXpathTest {
    object TestCls : Block(
        tagSelector("div")
    ) {
        val s = tagSelector("p")
    }

    init {
        TestCls.parse()
    }

    /**
     * Checks Require #1
     * @see [org.xpathqs.core.selector.block.Block.xpath]
     */
    @Test
    fun r1_xpath() {
        GIVEN {
            TestCls[2].s
        }.WHEN {
            given.xpath
            given.xpath
        }.THEN {
            "//div[position()=2]//p"
        }.AFTER {
            given.toXpath()
        }
    }

    /**
     * Checks Require #1
     * @see [org.xpathqs.core.selector.block.Block.toXpath]
     */
    @Test
    fun r1_toXpath() {
        GIVEN {
            TestCls[2].s
        }.WHEN {
            given.toXpath()
            given.toXpath()
        }.THEN {
            "//div//p"
        }
    }
}