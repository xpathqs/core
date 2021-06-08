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

package org.xpathqs.core.reflection

import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import assertk.assertions.isNull
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.xpathqs.core.selector.block.Block
import org.xpathqs.core.selector.extensions.core.get
import org.xpathqs.core.util.SelectorFactory.tagSelector

@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FIELD
)
@Retention(AnnotationRetention.RUNTIME)
annotation class TestAnnotation1

@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FIELD
)
@Retention(AnnotationRetention.RUNTIME)
annotation class TestAnnotation2

@TestAnnotation1
object PageWithAnnotations : Block() {
    @TestAnnotation2
    val s1 = tagSelector()
    val s2 = tagSelector()

    @TestAnnotation2
    @TestAnnotation1
    val s3 = tagSelector()
}

class SelectorAnnotationsTest {

    @Test
    fun checkBlock() {
        assertThat(PageWithAnnotations.annotations)
            .hasSize(1)
    }

    @Test
    fun checkSelector() {
        assertThat(PageWithAnnotations.s1.annotations)
            .hasSize(1)
    }

    @Test
    fun checkSelectorWithoutAnnotation() {
        assertThat(PageWithAnnotations.s2.annotations)
            .hasSize(0)
    }

    @Test
    fun checkSelectorWithTwoAnnotation() {
        assertThat(PageWithAnnotations.s3.annotations)
            .hasSize(2)
    }

    @Test
    fun annotationsShouldBeCloned() {
        assertThat(PageWithAnnotations.s3[2].annotations)
            .hasSize(2)
    }

    @Test
    fun noFieldForRootObj() {
        assertThat(PageWithAnnotations.field)
            .isNull()
    }

    @Test
    fun fieldForSelectors() {
        assertThat(PageWithAnnotations.s1.field)
            .isNotNull()

        assertThat(PageWithAnnotations.s1.field?.name)
            .isEqualTo("s1")
    }

    companion object {
        @JvmStatic
        @BeforeAll
        fun init() {
            SelectorParser(PageWithAnnotations).parse()
        }
    }
}