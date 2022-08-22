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

package org.xpathqs.core.selector.extensions

import assertk.assertAll
import org.junit.jupiter.api.Test
import org.xpathqs.core.reflection.freeze
import org.xpathqs.core.selector.extensions.core.get
import org.xpathqs.core.selector.selector.Selector
import org.xpathqs.core.selector.selector.postfix
import org.xpathqs.core.selector.selector.prefix
import org.xpathqs.core.selector.selector.tag
import org.xpathqs.core.util.SelectorFactory.tagSelector
import org.xpathqs.xpathShouldBe

class SelectorModificationTests {

    @Test
    fun tagTest() {
        val s1 = Selector().freeze()
        val s2 = s1.tag("s2")

        assertAll {
            s1.xpathShouldBe("//*")
            s2.xpathShouldBe("//s2")
        }
    }

    @Test
    fun removeParamsTest() {
        tagSelector("div")[2].removeParams()
            .xpathShouldBe("//div")
    }

    @Test
    fun prefixTest() {
        tagSelector("div").prefix("/")
            .xpathShouldBe("/div")
    }

    @Test
    fun postfixTest() {
        tagSelector("div").postfix("/..")
            .xpathShouldBe("//div/..")
    }

    @Test
    fun noBaseTest() {
        tagSelector("div").postfix("/..")
            .xpathShouldBe("//div/..")
    }

    @Test
    fun repeatTest() {
        tagSelector("div").repeat(3)
            .xpathShouldBe("//div//div//div")
    }

    /**
     * Checks Require #1 of [parentCount]
     */
    @Test
    fun parentCount_r1() {
        tagSelector("div").parentCount(2)
            .xpathShouldBe("//div/../..")
    }

    /**
     * Checks Require #2 of [parentCount]
     */
    @Test
    fun parentCount_r2() {
        tagSelector("div").parentCount(0)
            .xpathShouldBe("//div")
    }
}