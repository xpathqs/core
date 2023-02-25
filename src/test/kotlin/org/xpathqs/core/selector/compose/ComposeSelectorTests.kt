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

package org.xpathqs.core.selector.compose

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.string.shouldBeEmpty
import org.xpathqs.core.selector.extensions.core.get
import org.xpathqs.core.selector.extensions.div
import org.xpathqs.core.selector.extensions.followingSibling
import org.xpathqs.core.selector.extensions.preceding
import org.xpathqs.core.selector.extensions.precedingSibling
import org.xpathqs.core.selector.selector.prefix
import org.xpathqs.core.util.SelectorFactory.compose
import org.xpathqs.core.util.SelectorFactory.tagSelector
import org.xpathqs.xpathShouldBe

class ComposeSelectorTests : AnnotationSpec() {

    @Test
    fun composeFuncWithNoArgs() {
        compose()
            .xpathShouldBe("")
    }

    @Test
    fun composeFuncWithOneArgs() {
        compose(tagSelector("div"))
            .xpathShouldBe("//div")
    }

    @Test
    fun composeFuncWithTwoArgs() {
        compose(tagSelector("div"), tagSelector("div"))
            .xpathShouldBe("(//div) | (//div)")
    }

    @Test
    fun composeFuncWithTwoArgsAndPos() {
        compose(tagSelector("div"), tagSelector("div"))[2]
            .xpathShouldBe("((//div) | (//div))[position()=2]")
    }

    @Test
    fun divOperator() {
        val s = tagSelector("div") / tagSelector("div")
        s.xpathShouldBe("(//div) | (//div)")
    }

    @Test
    fun tag() {
        val s = tagSelector("div") / tagSelector("div")
        s.tag.shouldBeEmpty()
    }

    @Test
    fun divOperatorPriority() {
        val s = tagSelector("div")[2] / tagSelector("div")[3]
        s.xpathShouldBe("(//div[position()=2]) | (//div[position()=3])")
    }

    /**
     * Checks Require #1 of [following]
     */


    /**
     * Checks Require #1 of [followingSibling]
     */
    @Test
    fun followingSibling_r1() {
        (tagSelector("div") followingSibling tagSelector("div"))
            .xpathShouldBe("//div//following-sibling::div")
    }

    /**
     * Checks Require #1 of [preceding]
     */
    @Test
    fun preceding_r1() {
        (tagSelector("div") preceding tagSelector("div"))
            .xpathShouldBe("//div//preceding::div")
    }

    /**
     * Checks Require #1 of [precedingSibling]
     */
    @Test
    fun precedingSibling_r1() {
        (tagSelector("div") precedingSibling tagSelector("div"))
            .xpathShouldBe("//div//preceding-sibling::div")
    }

    @Test
    fun precedingSibling_withSinglePrefix() {
        (tagSelector("div") precedingSibling tagSelector("div").prefix("/"))
            .xpathShouldBe("//div/preceding-sibling::div")
    }
}