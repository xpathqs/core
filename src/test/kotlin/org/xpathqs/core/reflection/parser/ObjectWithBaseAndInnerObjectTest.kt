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
import io.kotest.matchers.shouldBe
import org.xpathqs.core.reflection.PageWithBaseAndInnerObject
import org.xpathqs.core.reflection.SelectorParser
import org.xpathqs.core.selector.extensions.core.get
import org.xpathqs.xpathShouldBe

class ObjectWithBaseAndInnerObjectTest : AnnotationSpec() {

    @BeforeEach
    fun before() {
        SelectorParser(PageWithBaseAndInnerObject).parse()
    }

    @Test
    fun checkInnerName() {
        PageWithBaseAndInnerObject.Inner.name shouldBe "PageWithBaseAndInnerObject.Inner"
    }

    @Test
    fun checkSelectorName() {
        PageWithBaseAndInnerObject.Inner.s1_inner.name shouldBe "PageWithBaseAndInnerObject.Inner.s1_inner"
    }

    @Test
    fun checkSelectorXpath() {
        PageWithBaseAndInnerObject.Inner.s1_inner
            .xpathShouldBe("//base//inner//inner_tag")
    }

    @Test
    fun checkPageChildren() {
        PageWithBaseAndInnerObject.Inner.children shouldHaveSize 1
    }

    @Test
    fun checkInnerPosition() {
        assertSoftly {
            PageWithBaseAndInnerObject.Inner.s1_inner
                .xpathShouldBe("//base//inner//inner_tag")

            PageWithBaseAndInnerObject.Inner[2].s1_inner
                .xpathShouldBe("//base//inner[position()=2]//inner_tag")

            PageWithBaseAndInnerObject.Inner.s1_inner
                .xpathShouldBe("//base//inner//inner_tag")
        }
    }
}
