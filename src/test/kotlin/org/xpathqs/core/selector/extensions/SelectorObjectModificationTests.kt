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

import assertk.assertAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.xpathqs.core.reflection.PageWithBase
import org.xpathqs.core.reflection.SelectorParser
import org.xpathqs.core.reflection.parse
import org.xpathqs.core.selector.block.Block
import org.xpathqs.core.selector.extensions.core.get
import org.xpathqs.core.selector.group.tag
import org.xpathqs.core.selector.selector.Selector
import org.xpathqs.core.selector.selector.SelectorProps
import org.xpathqs.core.selector.selector.tag
import org.xpathqs.xpathShouldBe

object PageWithBase2 : Block(
    Selector(
        props = SelectorProps(tag = "base")
    )
) {
    val s1 = Selector(props = SelectorProps(tag = "s1"))
}

class SelectorObjectModificationTests {

    @BeforeEach
    fun before() {
        SelectorParser(PageWithBase).parse()
    }

    @Test
    fun tagTest() {
        val s1 = PageWithBase
        val s2 = PageWithBase.tag("s2")

        assertAll {
            s1.xpathShouldBe("//base")
            s2.xpathShouldBe("//s2")
            s1.xpathShouldBe("//base")
        }
    }

    @Test
    fun positionTest() {
        val s1 = PageWithBase
        val s2 = PageWithBase[2]

        assertAll {
            s1.xpathShouldBe("//base")
            s2.xpathShouldBe("//base[position()=2]")
            s1.xpathShouldBe("//base")
        }
    }

    @Test
    fun positionSelTest() {
        PageWithBase2.parse()

        assertAll {
            PageWithBase2.s1
                .xpathShouldBe("//base//s1")
            PageWithBase2[2].s1
                .xpathShouldBe("//base[position()=2]//s1")
            PageWithBase2.s1
                .xpathShouldBe("//base//s1")
        }
    }

    @Test
    fun tagTestForInnerSelector() {
        assertAll {
            PageWithBase.tag("s2").s1
                .xpathShouldBe("//s2//s1")

            PageWithBase.s1
                .xpathShouldBe("//base//s1")

            PageWithBase[2].s1
                .xpathShouldBe("//base[position()=2]//s1")

            PageWithBase.s1
                .xpathShouldBe("//base//s1")
        }
    }

    @Test
    fun tagTestForInnerSelectorWithUpdate() {
        val s = PageWithBase.tag("s2").s1.tag("ss")

        assertAll {
            s.xpathShouldBe("//s2//ss")
            s.xpathShouldBe("//s2//ss")
        }
    }
}