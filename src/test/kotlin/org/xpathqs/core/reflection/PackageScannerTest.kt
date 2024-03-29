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

package org.xpathqs.core.reflection

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe
import org.xpathqs.core.reflection.packagescannertestpages.Page1
import org.xpathqs.core.reflection.packagescannertestpages.Page2
import org.xpathqs.core.reflection.packagescannertestpages.innerpackage.Page3

class PackageScannerTest : AnnotationSpec() {
    private val packageName = Page1::class.java.packageName

    @Test
    fun packageObjects() {
        PackageScanner(packageName)
            .packageObjects shouldContainExactlyInAnyOrder listOf(Page1, Page2, Page3)
    }

    @Test
    fun scan() {
        PackageScanner(packageName).scan()

        assertSoftly {
            Page1.name shouldBe "Page1"
            Page2.name shouldBe "Page2"
            Page3.name shouldBe "Page3"
        }
    }
}