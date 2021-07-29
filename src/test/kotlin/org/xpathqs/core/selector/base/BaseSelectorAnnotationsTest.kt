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

package org.xpathqs.core.selector.base

import assertk.assertThat
import assertk.assertions.isFalse
import assertk.assertions.isNotNull
import assertk.assertions.isNull
import assertk.assertions.isTrue
import org.junit.jupiter.api.Test
import org.xpathqs.core.annotations.NoScan
import org.xpathqs.core.reflection.scan
import org.xpathqs.core.selector.block.Block
import org.xpathqs.core.util.SelectorFactory.tagSelector

@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FIELD
)
@Retention(AnnotationRetention.RUNTIME)
annotation class TestAnnotation(
    val text: String = ""
)

@TestAnnotation
object Page1 : Block() {
    val s1 = tagSelector("div")
    object Inner: Block() {
        val s1 = tagSelector("div")
    }
}

class BaseSelectorAnnotationsTest {

    init {
        Page1::class.java.`package`.scan()
    }

    /**
     * Check Require #1 of [BaseSelector.hasParentAnnotation]
     */
    @Test
    fun r1_hasParentAnnotation() {
        assertThat(Page1.s1.hasParentAnnotation(TestAnnotation::class))
            .isTrue()
    }

    /**
     * Check Require #2 of [BaseSelector.hasParentAnnotation]
     */
    @Test
    fun r2_hasParentAnnotation() {
        assertThat(Page1.Inner.s1.hasParentAnnotation(TestAnnotation::class))
            .isFalse()
    }

    /**
     * Check Require #1 of [BaseSelector.hasAnyParentAnnotation]
     */
    @Test
    fun r1_hasAnyParentAnnotation() {
        assertThat(Page1.Inner.s1.hasAnyParentAnnotation(TestAnnotation::class))
            .isTrue()
    }

    /**
     * Check Require #2 of [BaseSelector.hasAnyParentAnnotation]
     */
    @Test
    fun r2_hasAnyParentAnnotation() {
        assertThat(Page1.Inner.s1.hasAnyParentAnnotation(NoScan::class))
            .isFalse()
    }

    /**
     * Check Require #1 of [BaseSelector.findAnnotation]
     */
    @Test
    fun r1_findAnnotation() {
        assertThat(Page1.findAnnotation<TestAnnotation>())
            .isNotNull()
    }

    /**
     * Check Require #2 of [BaseSelector.findAnnotation]
     */
    @Test
    fun r2_findAnnotation() {
        assertThat(Page1.findAnnotation<NoScan>())
            .isNull()
    }

    /**
     * Check Require #1 of [BaseSelector.findParentAnnotation]
     */
    @Test
    fun r1_findParentAnnotation() {
        assertThat(Page1.s1.findParentAnnotation<TestAnnotation>())
            .isNotNull()
    }

    /**
     * Check Require #2 of [BaseSelector.findParentAnnotation]
     */
    @Test
    fun r2_findParentAnnotation() {
        assertThat(Page1.Inner.s1.findParentAnnotation<TestAnnotation>())
            .isNull()
    }

    /**
     * Check Require #1 of [BaseSelector.findAnyParentAnnotation]
     */
    @Test
    fun r1_findAnyParentAnnotation() {
        assertThat(Page1.Inner.s1.findAnyParentAnnotation<TestAnnotation>())
            .isNotNull()
    }

    /**
     * Check Require #2 of [BaseSelector.findAnyParentAnnotation]
     */
    @Test
    fun r2_findAnyParentAnnotation() {
        assertThat(Page1.Inner.s1.findParentAnnotation<NoScan>())
            .isNull()
    }
}