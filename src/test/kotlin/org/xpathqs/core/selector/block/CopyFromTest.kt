/*
 * Copyright (c) 2026 XPATH-QS
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

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import org.xpathqs.core.reflection.parse
import org.xpathqs.core.selector.ComposeSelector
import org.xpathqs.core.selector.ISelector
import org.xpathqs.core.selector.extensions.plus
import org.xpathqs.core.selector.GroupSelector
import org.xpathqs.core.selector.NullSelector
import org.xpathqs.core.selector.Selector
import org.xpathqs.core.selector.XpathSelector
import org.xpathqs.core.selector.extensions.core.get
import org.xpathqs.core.selector.extensions.result
import org.xpathqs.core.util.SelectorFactory.compose
import org.xpathqs.core.util.SelectorFactory.tagSelector
import org.xpathqs.core.util.SelectorFactory.xpathSelector
import org.xpathqs.gwt.GIVEN

class CopyFromTest : AnnotationSpec() {

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
        constructor(sel: ComposeSelector): this() {
            copyFrom(sel)
        }
        val s = tagSelector("div")
    }

    class TestCls2(base: ISelector): Block(base) {
        val s = tagSelector("div")
    }

    @Test
    fun isBlackTest() {
        TestCls().parse().isBlank shouldBe true
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
            "//div1//div" shouldBe actual
        }
    }

    @Test
    fun copyFromSelector2() {
        GIVEN {
            TestCls2(
                tagSelector("div1")
            ).parse()
        }.WHEN {
            given.s.toXpath()
        }.THEN {
            "//div1//div" shouldBe actual
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
            "//div1//div" shouldBe actual
        }
    }

    @Test
    fun copyFromXpathSelector2() {
        GIVEN {
            TestCls2(
                xpathSelector("//div1")
            ).parse()
        }.WHEN {
            given.s.toXpath()
        }.THEN {
            "//div1//div" shouldBe actual
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
            "//div1//div2//div" shouldBe actual
        }
    }

    @Test
    fun copyFromGroupResultSelector() {
        GIVEN {
            TestCls2(
                (tagSelector("div1") + tagSelector("div2")).result[2]
            ).parse()
        }.WHEN {
            given.s.toXpath()
        }.THEN {
            "(//div1//div2)[position()=2]//div" shouldBe actual
        }
    }

    @Test
    fun copyFromComposeSelector2() {
        GIVEN {
            TestCls2(
                compose(tagSelector("div"), tagSelector("a"))
            ).parse()
        }.WHEN {
            given.s.toXpath()
        }.THEN {
            "(//div) | (//a)//div" shouldBe actual
        }
    }

    @Test
    fun copyFromComposeSelector() {
        GIVEN {
            TestCls(
                compose(tagSelector("div"), tagSelector("a"))
            ).parse()
        }.WHEN {
            given.s.toXpath()
        }.THEN {
            "((//div) | (//a))//div" shouldBe actual
        }
    }

    @Test
    fun copyFromNullSelector() {
        GIVEN {
            TestCls2(
                NullSelector()
            ).parse()
        }.WHEN {
            given.s.toXpath()
        }.THEN {
            "//div" shouldBe actual
        }
    }

    @Test
    fun copyFromGroupSelector2() {
        GIVEN {
            TestCls2(
                tagSelector("div1") + tagSelector("div2")
            ).parse()
        }.WHEN {
            given.s.toXpath()
        }.THEN {
            "//div1//div2//div" shouldBe actual
        }
    }
}