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
import org.xpathqs.core.reflection.parse
import org.xpathqs.core.selector.block.Block
import org.xpathqs.core.util.SelectorFactory.tagSelector
import org.xpathqs.gwt.WHEN

class BaseSelectorToStringTest : AnnotationSpec() {
    class TestCls: Block() {
        val s1 = tagSelector("div")
    }

    /**
     * Checks Require #1
     * @see BaseSelector.toString
     */
    @Test
    fun r1_notEmptyName() =
        WHEN {
            TestCls().parse().s1.toString()
        }.THEN {
            "TestCls.s1"
        }

    /**
     * Checks Require #2
     * @see BaseSelector.toString
     */
    @Test
    fun r2_emptyName() =
        WHEN {
            tagSelector("div").toString()
        }.THEN {
            "//div"
        }
}