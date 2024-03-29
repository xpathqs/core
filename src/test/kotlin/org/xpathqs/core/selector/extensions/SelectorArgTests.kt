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

import io.kotest.core.spec.style.AnnotationSpec
import org.xpathqs.core.selector.extensions.core.get
import org.xpathqs.core.selector.selector.preceding
import org.xpathqs.core.util.SelectorFactory.tagSelector
import org.xpathqs.xpathShouldBe

class SelectorArgTests : AnnotationSpec() {

    @Test
    fun onlyText() {
        tagSelector("div").text("test")
            .xpathShouldBe("//div[text()='test']")
    }

    @Test
    fun textAndPosition() {
        tagSelector("div").text("test")[2]
            .xpathShouldBe("//div[text()='test' and position()=2]")
    }

    @Test
    fun textContainsAndPosition() {
        tagSelector("div").text("test", contains = true)[2]
            .xpathShouldBe("//div[contains(text(), 'test') and position()=2]")
    }

    @Test
    fun onlyId() {
        tagSelector("div").id("test")
            .xpathShouldBe("//div[@id='test']")
    }

    @Test
    fun idAndPosition() {
        tagSelector("div").id("test")[2]
            .xpathShouldBe("//div[@id='test' and position()=2]")
    }

    @Test
    fun idContainsAndPosition() {
        tagSelector("div").id("test", contains = true)[2]
            .xpathShouldBe("//div[contains(@id, 'test') and position()=2]")
    }

    @Test
    fun textNotEmpty() {
        tagSelector("div").textNotEmpty()
            .xpathShouldBe("//div[string-length(text()) > 0]")
    }

    @Test
    fun normalizedTextNotEmpty() {
        tagSelector("div").normalizedTextNotEmpty()
            .xpathShouldBe("//div[string-length(normalize-space(text())) > 0]")
    }

    @Test
    fun textWithContainsAndNormalize() {
        tagSelector("div").text("text", contains = true, normalize = true)
            .xpathShouldBe("//div[contains(normalize-space(text()), 'text')]")
    }

    @Test
    fun textWithNormalize() {
        tagSelector("div").text("text", normalize = true)
            .xpathShouldBe("//div[normalize-space(text())='text']")
    }

    /**
     * Checks require #1
     * @see org.xpathqs.core.selector.extensions.withAttribute
     */
    @Test
    fun r1_withAttribute() {
        tagSelector("div").withAttribute("a")
            .xpathShouldBe("//div[@a]")
    }

    /**
     * Checks require #2
     * @see org.xpathqs.core.selector.extensions.withAttribute
     */
    @Test
    fun r2_withAttribute() {
        tagSelector("div").withAttribute(value = "a")
            .xpathShouldBe("//div[@*='a']")
    }

    /**
     * Checks require #3
     * @see org.xpathqs.core.selector.extensions.withAttribute
     */
    @Test
    fun r3_withAttribute() {
        tagSelector("div").withAttribute(name = "n", value = "a")
            .xpathShouldBe("//div[@n='a']")
    }

    /**
     * Checks require #1
     * @see org.xpathqs.core.selector.extensions.childCount
     */
    @Test
    fun r1_childCount() {
        tagSelector("div").childCount(0)
            .xpathShouldBe("//div[count(*)=0]")
    }

    @Test
    fun testWithSelector() {
        tagSelector("div")[tagSelector("div").preceding()]
            .xpathShouldBe("//div[.//preceding::div]")
    }

    @Test
    fun testWithSelectorContains() {
        (tagSelector("div") contains tagSelector("t").preceding())
            .xpathShouldBe("//div[./preceding::t]")
    }

    @Test
    fun testWithSelectorContainsAny() {
        (tagSelector("div") containsAny tagSelector("t").preceding())
            .xpathShouldBe("//div[.//preceding::t]")
    }
}