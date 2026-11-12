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

package org.xpathqs.core.selector.block.extensions

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.xpathqs.core.reflection.parse
import org.xpathqs.core.selector.block.Block
import org.xpathqs.core.selector.block.findAllWithAnnotation
import org.xpathqs.core.selector.block.findWithAnnotation
import org.xpathqs.core.util.SelectorFactory.tagSelector

class AnnotationsTest : AnnotationSpec() {
    annotation class TestAnnotation1
    annotation class TestAnnotation2

    @TestAnnotation1
    object TestAnnBlock : Block()

    object TestAnnBlock3 : Block() {
        @TestAnnotation1
        val s1 = tagSelector("div")

        @TestAnnotation1
        @TestAnnotation2
        val s2 = tagSelector("div")
    }

    init {
        TestAnnBlock.parse()
        TestAnnBlock3.parse()
    }

    @Test
    fun findAllWithAnnotationForEmptyBlock() {
        val r = TestAnnBlock.findAllWithAnnotation(TestAnnotation1::class)
        r.size shouldBe 0
    }

    @Test
    fun findWithAnnotationForEmptyBlock() {
        val r = TestAnnBlock.findWithAnnotation(TestAnnotation1::class)
        r shouldBe null
    }

    @Test
    fun findAllWithAnnotationForEmptyBlock2() {
        val r = TestAnnBlock3.findAllWithAnnotation(TestAnnotation1::class)
        r.size shouldBe 2
    }

    @Test
    fun findWithAnnotationForEmptyBlock2() {
        val r = TestAnnBlock3.findWithAnnotation(TestAnnotation1::class)
        r shouldNotBe null
        r!!.name shouldBe "TestAnnBlock3.s1"
    }

    @Test
    fun findWithAnnotationForEmptyBlock_2() {
        val r = TestAnnBlock3.findWithAnnotation(TestAnnotation2::class)
        r shouldNotBe null
        r!!.name shouldBe "TestAnnBlock3.s2"
    }
}