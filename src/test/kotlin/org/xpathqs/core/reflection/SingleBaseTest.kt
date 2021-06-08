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

package org.xpathqs.core.reflection

import org.junit.jupiter.api.Test
import org.xpathqs.core.annotations.SingleBase
import org.xpathqs.core.selector.block.Block
import org.xpathqs.core.selector.extensions.plus
import org.xpathqs.core.util.SelectorFactory.tagSelector
import org.xpathqs.core.util.SelectorFactory.xpathSelector
import org.xpathqs.gwt.GIVEN
import org.xpathqs.gwt.WHEN

class SingleBaseTest {
    class ForTest: Block(tagSelector("base")) {
        @SingleBase
        val s1 = tagSelector("div")
        @SingleBase
        val s2 = tagSelector("div") + tagSelector("div")
    }

    object ForTestObj: Block() {
        @SingleBase
        object InnerObj: Block(tagSelector("base")) {
            val s1 = tagSelector("div")
            val s2 = xpathSelector("/div")
            val s3 = xpathSelector("//div")
        }
    }

    /**
     * Checks #1 require
     * @see org.xpathqs.core.annotations.SingleBase
     */
    @Test
    fun r1_forSelector() =
        WHEN {
            ForTest().parse().s1.toXpath()
        }.THEN {
            "//base/div"
        }

    /**
     * Checks #2 require
     * @see org.xpathqs.core.annotations.SingleBase
     */
    @Test
    fun r2_forBlock() =
        GIVEN {
            ForTestObj.parse()
        }.WHEN {
            ForTestObj.InnerObj.s1.toXpath()
        }.THEN {
            "//base/div"
        }

    /**
     * Checks #3 require
     * @see org.xpathqs.core.annotations.SingleBase
     */
    @Test
    fun r3_forGroupSelector() =
        WHEN {
            ForTest().parse().s2.toXpath()
        }.THEN {
            "//base/div//div"
        }

    /**
     * Checks #4 require
     * @see org.xpathqs.core.annotations.SingleBase
     */
    @Test
    fun r4_forXpathSelector1() =
        GIVEN {
            ForTestObj.parse()
        }.WHEN {
            ForTestObj.InnerObj.s2.toXpath()
        }.THEN {
            "//base/div"
        }

    /**
     * Checks #4 require
     * @see org.xpathqs.core.annotations.SingleBase
     */
    @Test
    fun r4_forXpathSelector2() =
        GIVEN {
            ForTestObj.parse()
        }.WHEN {
            ForTestObj.InnerObj.s3.toXpath()
        }.THEN {
            "//base//div"
        }
}
