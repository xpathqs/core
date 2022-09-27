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

package org.xpathqs.core.selector.result

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.string.shouldBeEmpty

import org.xpathqs.core.selector.extensions.core.get
import org.xpathqs.core.selector.extensions.result
import org.xpathqs.core.selector.extensions.textNotEmpty
import org.xpathqs.core.util.SelectorFactory.tagSelector
import org.xpathqs.xpathShouldBe

class ResultSelectorTest : AnnotationSpec() {

    @Test
    fun toXpath() {
        ResultSelector(tagSelector()).xpathShouldBe("(//*)")
    }

    @Test
    fun toXpathWithBaseAndPos() {
        ResultSelector(
            wrapper = tagSelector("div")
        )[2].xpathShouldBe("(//div)[position()=2]")
    }

    @Test
    fun getTag() {
        ResultSelector(tagSelector()).tag.shouldBeEmpty()
    }

    @Test
    fun resultTest() {
        tagSelector("div").result[2]
            .xpathShouldBe("(//div)[position()=2]")
    }

    @Test
    fun resultTest2() {
        tagSelector("div").textNotEmpty().result[2]
            .xpathShouldBe("(//div[string-length(text()) > 0])[position()=2]")
    }
}