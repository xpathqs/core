/*
 * Copyright (c) 2021 Nikita A. Chegodaev
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

import org.junit.jupiter.api.Test
import org.xpathqs.core.util.SelectorFactory.tagSelector
import org.xpathqs.xpathShouldBe

class TextModificationTests {

    @Test
    fun defaultText() {
        tagSelector("div").text("ok")
            .xpathShouldBe("//div[text()='ok']")
    }

    @Test
    fun defaultTextWithComma() {
        tagSelector("div").text("don't")
            .xpathShouldBe("//div[text()=concat('don',\"'\",'t')]")
    }

    @Test
    fun textNormalized() {
        tagSelector("div").text("ok", normalize = true)
            .xpathShouldBe("//div[text()=normalize-space('ok')]")
    }

    @Test
    fun textContains() {
        tagSelector("div").text("ok", contains = true)
            .xpathShouldBe("//div[contains(text(), 'ok')]")
    }

    @Test
    fun textContainsAndNormalized() {
        tagSelector("div").text("ok", contains = true, normalize = true)
            .xpathShouldBe("//div[contains(text(), normalize-space('ok'))]")
    }
}