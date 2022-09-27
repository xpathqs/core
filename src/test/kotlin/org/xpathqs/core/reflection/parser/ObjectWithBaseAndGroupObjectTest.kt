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


import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.xpathqs.core.reflection.PageWithBaseAndInnerGroupObject
import org.xpathqs.core.reflection.SelectorParser
import org.xpathqs.xpathShouldBe


internal class ObjectWithBaseAndGroupObjectTest : AnnotationSpec() {

    @BeforeEach
    fun before() {
        SelectorParser(PageWithBaseAndInnerGroupObject).parse()
    }

    @Test
    fun checkInnerName() {
        PageWithBaseAndInnerGroupObject.Inner.name shouldBe "PageWithBaseAndInnerGroupObject.Inner"
    }

    @Test
    fun checkSelectorName() {
        PageWithBaseAndInnerGroupObject.Inner.s1_inner.name shouldBe "PageWithBaseAndInnerGroupObject.Inner.s1_inner"
    }

    @Test
    fun checkSelectorXpath() {
        PageWithBaseAndInnerGroupObject.Inner.s1_inner
            .xpathShouldBe("//base//div//p//inner_tag")
    }

    @Test
    fun checkPageChildren() {
        PageWithBaseAndInnerGroupObject.Inner.children shouldHaveSize 1
    }
}
