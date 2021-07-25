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

import org.junit.jupiter.api.Test
import org.xpathqs.core.reflection.testpage1.Page2
import org.xpathqs.core.reflection.testpage2.Page3
import org.xpathqs.core.selector.block.Block
import org.xpathqs.core.util.SelectorFactory.tagSelector
import org.xpathqs.gwt.GIVEN

object Page1 : Block() {
    val s1 = tagSelector("div")
}

class PackageScannerExtensions {

    /**
     * Test of [org.xpathqs.core.reflection.scan]
     */
    @Test
    fun packageMethodScan() {
        GIVEN {
            this.javaClass.`package`.scan()
        }.WHEN {
            Page1.s1.name
        }.THEN {
            "Page1.s1"
        }
    }

    /**
     * Test of [org.xpathqs.core.reflection.scanPackage]
     */
    @Test
    fun packageStringScan() {
        GIVEN {
            "org.xpathqs.core.reflection.testpage2".scanPackage()
        }.WHEN {
            Page3.s1.name
        }.THEN {
            "Page3.s1"
        }
    }

    /**
     * Test of [org.xpathqs.core.reflection.scanPackage]
     */
    @Test
    fun packageMethodArgScan() {
        GIVEN {
            scanPackage(Page2)
        }.WHEN {
            Page2.s1.name
        }.THEN {
            "Page2.s1"
        }
    }
}