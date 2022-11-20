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

package org.xpathqs.core.selector.base

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.xpathqs.core.annotations.NoScan
import org.xpathqs.core.reflection.scan
import org.xpathqs.core.selector.block.Block
import org.xpathqs.core.util.SelectorFactory.tagSelector
import kotlin.reflect.jvm.kotlinProperty

@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.PROPERTY
)
@Retention(AnnotationRetention.RUNTIME)
@Repeatable
@ExperimentalStdlibApi
annotation class TestAnnotation(
    val text: String = ""
)

@OptIn(ExperimentalStdlibApi::class)
@TestAnnotation
object Page1 : Block() {
    val s1 = tagSelector("div")
    object Inner: Block() {
        val s1 = tagSelector("div")
    }

    @TestAnnotation(text = "s1")
    @TestAnnotation(text = "s2")
    val s2 = tagSelector("div")
}

@OptIn(ExperimentalStdlibApi::class)
class BaseSelectorAnnotationsTest : AnnotationSpec() {

    init {
        Page1::class.java.`package`.scan()
    }

    /**
     * Check Require #1 of [BaseSelector.hasParentAnnotation]
     */
    @Test
    fun r1_hasParentAnnotation() {
        Page1.s1.hasParentAnnotation(TestAnnotation::class) shouldBe true
    }

    /**
     * Check Require #2 of [BaseSelector.hasParentAnnotation]
     */
    @Test
    fun r2_hasParentAnnotation() {
        Page1.Inner.s1.hasParentAnnotation(TestAnnotation::class) shouldBe false
    }


    /**
     * Check Require #1 of [BaseSelector.hasAnyParentAnnotation]
     */
    @Test
    fun r1_hasAnyParentAnnotation() {
        Page1.Inner.s1.hasAnyParentAnnotation(TestAnnotation::class) shouldBe true
    }

    /**
     * Check Require #2 of [BaseSelector.hasAnyParentAnnotation]
     */
    @Test
    fun r2_hasAnyParentAnnotation() {
        Page1.Inner.s1.hasAnyParentAnnotation(NoScan::class) shouldBe false
    }

    /**
     * Check Require #1 of [BaseSelector.findAnnotation]
     */
    @Test
    fun r1_findAnnotation() {
        Page1.findAnnotation<TestAnnotation>() shouldNotBe null
    }

   /**
     * Check Require #2 of [BaseSelector.findAnnotation]
     */
    @Test
    fun r2_findAnnotation() {
        Page1.findAnnotation<NoScan>() shouldBe null
    }

    @Test
    fun r1_findAnnotations() {
        val r =Page1.s2.field?.kotlinProperty?.annotations
        println(r)
    }

    /**
     * Check Require #1 of [BaseSelector.findParentAnnotation]
     */
    @Test
    fun r1_findParentAnnotation() {
        Page1.s1.findParentAnnotation<TestAnnotation>() shouldNotBe null
    }

    /**
     * Check Require #2 of [BaseSelector.findParentAnnotation]
     */
    @Test
    fun r2_findParentAnnotation() {
        Page1.Inner.s1.findParentAnnotation<TestAnnotation>() shouldBe null
    }

    /**
     * Check Require #1 of [BaseSelector.findAnyParentAnnotation]
     */
    @Test
    fun r1_findAnyParentAnnotation() {
        Page1.Inner.s1.findAnyParentAnnotation<TestAnnotation>() shouldNotBe null
    }

    /**
     * Check Require #2 of [BaseSelector.findAnyParentAnnotation]
     */
    @Test
    fun r2_findAnyParentAnnotation() {
        Page1.Inner.s1.findParentAnnotation<NoScan>() shouldBe null
    }

    /**
     * Check Require #1 of [BaseSelector.findParentWithAnnotation]
     */
    @Test
    fun r1_findParentWithAnnotation() {
        Page1.s1.findParentWithAnnotation(TestAnnotation::class) shouldNotBe null
    }

    /**
     * Check Require #2 of [BaseSelector.findParentWithAnnotation]
     */
    @Test
    fun r2_findParentWithAnnotation() {
        Page1.s1.findParentWithAnnotation(NoScan::class) shouldBe null
    }
}
