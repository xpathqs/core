/*
 * Copyright (c) 2023 XPATH-QS
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

package org.xpathqs.core.reflection.annotations

import io.kotest.core.spec.style.AnnotationSpec
import org.xpathqs.core.reflection.scanPackage
import org.xpathqs.gwt.WHEN

class FullNameAnnotationTest : AnnotationSpec() {
    init {
        scanPackage(this)
    }

    @Test
    fun t1() {
        WHEN {
            Page.s1.fullName
        }.THEN(
            "org.xpathqs.core.reflection.annotations.Page.s1"
        )
    }


    @Test
    fun t2() {
        WHEN {
            Page6.ss1.fullName
        }.THEN(
            "org.xpathqs.core.reflection.annotations.Page6.ss1"
        )
    }

    @Test
    fun t3() {
        WHEN {
            Page7.ss1.fullName
        }.THEN(
            "org.xpathqs.core.reflection.annotations.Page7.ss1"
        )
    }

    @Test
    fun t4() {
        WHEN {
            Page7.ss2.s.fullName
        }.THEN(
            "org.xpathqs.core.reflection.annotations.Page7.ss2.s"
        )
    }
}