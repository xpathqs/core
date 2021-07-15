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

package org.xpathqs.core.util

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.xpathqs.core.util.SelectorFactory.idContainsSelector
import org.xpathqs.core.util.SelectorFactory.idSelector
import org.xpathqs.core.util.SelectorFactory.tagSelector
import org.xpathqs.core.util.SelectorFactory.textContainsSelector
import org.xpathqs.core.util.SelectorFactory.textSelector
import org.xpathqs.xpathShouldBe

internal class SelectorFactoryTest {

    @Test
    fun tagSelector() {
        assertThat(tagSelector("div").toXpath())
            .isEqualTo("//div")
    }

    @Test
    fun textSelector() {
        assertThat(textSelector("div").toXpath())
            .isEqualTo("//*[text()='div']")
    }

    @Test
    fun textContainsSelector() {
        assertThat(textContainsSelector("some text").toXpath())
            .isEqualTo("//*[contains(text(), 'some text')]")
    }

    @Test
    fun idSelector() {
        idSelector("id")
            .xpathShouldBe("//*[@id='id']")
    }

    @Test
    fun idContainsSelector() {
        idContainsSelector("id")
            .xpathShouldBe("//*[contains(@id, 'id')]")
    }
}