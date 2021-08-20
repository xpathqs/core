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
import org.xpathqs.core.selector.extensions.plus
import org.xpathqs.core.selector.group.GroupSelector
import org.xpathqs.core.selector.selector.Selector
import org.xpathqs.core.selector.xpath.XpathSelector
import org.xpathqs.core.util.SelectorFactory.tagSelector
import org.xpathqs.core.util.SelectorFactory.xpathSelector
import org.xpathqs.gwt.GIVEN

class CopyFromTest {

    class TestCls(): Block() {
        constructor(sel: Selector): this() {
            copyFrom(sel)
        }
        constructor(sel: XpathSelector): this() {
            copyFrom(sel)
        }
        constructor(sel: GroupSelector): this() {
            copyFrom(sel)
        }
        val s = tagSelector("div")
    }

    /**
     * Checks [Block.copyFrom]
     */
    @Test
    fun copyFromSelector() {
        GIVEN {
            TestCls(
                tagSelector("div1")
            ).parse()
        }.WHEN {
            given.s.toXpath()
        }.THEN {
            "//div1//div"
        }
    }

    /**
     * Checks [Block.copyFrom]
     */
    @Test
    fun copyFromXpathSelector() {
        GIVEN {
            TestCls(
                xpathSelector("//div1")
            ).parse()
        }.WHEN {
            given.s.toXpath()
        }.THEN {
            "//div1//div"
        }
    }

    /**
     * Checks [Block.copyFrom]
     */
    @Test
    fun copyFromGroupSelector() {
        GIVEN {
            TestCls(
                tagSelector("div1") + tagSelector("div2")
            ).parse()
        }.WHEN {
            given.s.toXpath()
        }.THEN {
            "//div1//div2//div"
        }
    }
}