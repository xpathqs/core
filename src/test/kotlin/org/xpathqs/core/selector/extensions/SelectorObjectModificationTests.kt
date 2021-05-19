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

package org.xpathqs.core.selector.extensions

import assertk.assertAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.xpathqs.core.reflection.PageWithBase
import org.xpathqs.core.reflection.SelectorParser
import org.xpathqs.xpathShouldBe

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