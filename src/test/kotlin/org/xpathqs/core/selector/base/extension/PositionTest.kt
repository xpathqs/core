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

package org.xpathqs.core.selector.base.extension


import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import org.xpathqs.core.reflection.parse
import org.xpathqs.core.selector.base.ISelector
import org.xpathqs.core.selector.block.Block
import org.xpathqs.core.selector.extensions.core.get
import org.xpathqs.core.selector.extensions.plus
import org.xpathqs.core.selector.extensions.result
import org.xpathqs.core.util.SelectorFactory.tagSelector
import org.xpathqs.core.util.SelectorFactory.textSelector
import org.xpathqs.core.util.SelectorFactory.xpathSelector
import org.xpathqs.xpathShouldBe

object PositionTestPage: Block(textSelector("base1")) {
    val inner = Inner1(textSelector("s3"))
    val inner2 = Inner1(xpathSelector ("//div"))
    val inner3 = Inner1(tagSelector("div") + tagSelector("span"))
    val inner4 = Inner1(
        (tagSelector("div") + tagSelector("span")).result[2]
    )

    open class Inner1(
    ): Block() {
        val sel = textSelector("sel")

        constructor(sel: ISelector): this() {
            copyFrom(sel)
        }
    }
}

class PositionTest : AnnotationSpec() {
    init {
        PositionTestPage.parse()
    }

    @Test
    fun test1() {
        PositionTestPage.inner.xpathShouldBe("//*[text()='base1']//*[text()='s3']")
        PositionTestPage.inner[2].xpathShouldBe("//*[text()='base1']//*[text()='s3' and position()=2]")
        PositionTestPage.inner.xpathShouldBe("//*[text()='base1']//*[text()='s3']")
        PositionTestPage.inner3.isBlank shouldBe false
    }

    @Test
    fun test2() {
        PositionTestPage.inner2.xpathShouldBe("//*[text()='base1']//div")
        PositionTestPage.inner2[2].xpathShouldBe("//*[text()='base1']//div[position()=2]")
        PositionTestPage.inner2.xpathShouldBe("//*[text()='base1']//div")
        PositionTestPage.inner3.isBlank shouldBe false
    }

    @Test
    fun test3() {
        PositionTestPage.inner3.xpathShouldBe("//*[text()='base1']//div//span")
        PositionTestPage.inner3[2].xpathShouldBe("//*[text()='base1'](//div//span)[position()=2]")
        PositionTestPage.inner3.xpathShouldBe("//*[text()='base1']//div//span")
        PositionTestPage.inner3.isBlank shouldBe false
    }

    @Test
    fun test4() {
        PositionTestPage.inner4.xpathShouldBe("//*[text()='base1'](//div//span)[position()=2]")
        PositionTestPage.inner4[3].xpathShouldBe("//*[text()='base1'](//div//span)[position()=3]")
        PositionTestPage.inner4.xpathShouldBe("//*[text()='base1'](//div//span)[position()=2]")
        PositionTestPage.inner4.isBlank shouldBe false
    }
}