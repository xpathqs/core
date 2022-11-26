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

package org.xpathqs.core.reflection.parser

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.collections.shouldHaveSize
import org.xpathqs.core.reflection.*
import org.xpathqs.core.selector.extensions.core.get
import org.xpathqs.nameShouldBe
import org.xpathqs.xpathShouldBe


class ObjectWithInnerObjectClassArgTest : AnnotationSpec() {
    @BeforeEach
    fun parse() {
        SelectorParser(PageWithInnerObjectClassArg)
            .parse()
    }

    @Test
    fun innerSelectorFields() {
        SelectorReflectionFields(PageWithInnerObjectClassArg.Holder1)
            .innerSelectorProps shouldHaveSize 3
    }

    @Test
    fun xpath() {
        PageWithInnerObjectClassArg.Holder1
            .xpathShouldBe("//base")
    }

    @Test
    fun testSelectorFromClass() {
        assertSoftly {
            PageWithInnerObjectClassArg.Holder1.sel1
                .xpathShouldBe("//base//s1")
                .nameShouldBe("PageWithInnerObjectClassArg.Holder1.sel1")
        }
    }

    @Test
    fun testSelectorFromClassWithPos() {
        assertSoftly {
            PageWithInnerObjectClassArg.Holder1.sel1[2]
                .xpathShouldBe("//base//s1[position()=2]")
        }
    }

    @Test
    fun testSelectorFromClassWithBasePos() {
        assertSoftly {
            PageWithInnerObjectClassArg.Holder1[2].sel1
                .xpathShouldBe("//base[position()=2]//s1")

            PageWithInnerObjectClassArg.Holder1.sel1
                .xpathShouldBe("//base//s1")
        }
    }
}
