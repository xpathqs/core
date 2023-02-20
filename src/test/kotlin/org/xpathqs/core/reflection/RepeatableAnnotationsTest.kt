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

package org.xpathqs.core.reflection

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.collections.shouldHaveSize
import org.xpathqs.core.selector.block.Block
import org.xpathqs.core.util.SelectorFactory.tagSelector

@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.PROPERTY
)
@Retention(AnnotationRetention.RUNTIME)
@Repeatable
annotation class MultipleAnnotation1(
    val name: String = "",
    val arr: Array<String> = []
)


object PageWithRepeatableAnnotations : Block() {
    @MultipleAnnotation1(name = "n1", arr = ["b1"])
    @MultipleAnnotation1(name = "2", arr = ["a1", "a2"])
    val s1 = tagSelector()
}

class RepeatableAnnotationsTest : AnnotationSpec() {

    //TODO: Not working due to bug in kotlin, will be fixed on 1.8.20
    /*@Test
    fun checkBlock() {
        SelectorParser(PageWithRepeatableAnnotations).parse()

        val c = PageWithRepeatableAnnotations::class.java

        val jclass = PageWithRepeatableAnnotations.s1

        PageWithRepeatableAnnotations.s1.annotations.filterIsInstance<MultipleAnnotation1>().forEach {
            it.arr
        }
      //  PageWithRepeatableAnnotations.s1.annotations shouldHaveSize 2
    }
*/
    init {

    }
}