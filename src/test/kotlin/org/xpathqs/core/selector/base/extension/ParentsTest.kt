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

package org.xpathqs.core.selector.base.extension

import org.junit.jupiter.api.Test
import org.xpathqs.core.reflection.scanPackage
import org.xpathqs.core.selector.block.Block
import org.xpathqs.core.selector.extensions.parents
import org.xpathqs.core.util.SelectorFactory.tagSelector
import org.xpathqs.gwt.WHEN

object Page1 : Block() {
    val s1 = tagSelector("div")

    object Inner : Block() {
        val s1 = tagSelector("div")
    }
}

class ParentsTest {

    init {
        scanPackage(this)
    }

    /**
     * Checks Require #1 of [org.xpathqs.core.selector.base.BaseSelector.parents]
     */
    @Test
    fun r1_parents() {
        WHEN {
            tagSelector("div").parents
        }.THEN {
            emptyList()
        }
    }

    /**
     * Checks Require #2 of [org.xpathqs.core.selector.base.BaseSelector.parents]
     */
    @Test
    fun r2_parents() {
        WHEN {
            Page1.s1.parents
        }.THEN {
            arrayListOf(Page1)
        }
    }

    /**
     * Checks Require #3 of [org.xpathqs.core.selector.base.BaseSelector.parents]
     */
    @Test
    fun r3_parents() {
        WHEN {
            Page1.Inner.s1.parents
        }.THEN {
            arrayListOf(Page1.Inner, Page1)
        }
    }
}