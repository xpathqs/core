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

package org.xpathqs.core.selector.args

import io.kotest.core.spec.style.AnnotationSpec
import org.xpathqs.core.selector.selector.preceding
import org.xpathqs.core.selector.selector.prefix
import org.xpathqs.core.util.SelectorFactory.tagSelector
import org.xpathqs.core.util.SelectorFactory.xpathSelector
import org.xpathqs.gwt.GIVEN

class SelectorArgTest : AnnotationSpec() {

    /**
     * Checks Require #1 of [SelectorArg]
     */
    @Test
    fun r1() {
        GIVEN {
            tagSelector("div")
        }.WHEN {
            SelectorArg(given)
                .value
        }.THEN {
            given.toXpath()
        }
    }

    /**
     * Checks Require #2 of [SelectorArg]
     */
    @Test
    fun r2() {
        GIVEN {
            tagSelector("div")
        }.WHEN {
            SelectorArg(given, type = InnerSelectorArg.ALL)
                .value
        }.THEN {
            given.prefix(".//").toXpath()
        }
    }

    /**
     * Checks Require #3 of [SelectorArg]
     */
    @Test
    fun r3() {
        GIVEN {
            tagSelector("div")
        }.WHEN {
            SelectorArg(given, type = InnerSelectorArg.ROOT)
                .value
        }.THEN {
            given.prefix("./").toXpath()
        }
    }

    /**
     * Checks Require #4 of [SelectorArg]
     */
    @Test
    fun r4() {
        GIVEN {
            xpathSelector("//div")
        }.WHEN {
            SelectorArg(given, type = InnerSelectorArg.ROOT)
                .value
        }.THEN {
            given.toXpath()
        }
    }

    /**
     * Checks Require #4 of [SelectorArg]
     */
    @Test
    fun r5() {
        GIVEN {
            tagSelector("div").preceding()
        }.WHEN {
            SelectorArg(given, type = InnerSelectorArg.ROOT)
                .value
        }.THEN {
            "./preceding::div"
        }
    }
}