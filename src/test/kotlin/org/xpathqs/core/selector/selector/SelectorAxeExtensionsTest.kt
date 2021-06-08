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

package org.xpathqs.core.selector.selector

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.xpathqs.core.util.SelectorFactory.tagSelector
import org.xpathqs.xpathShouldBe

internal class SelectorAxeExtensionsTest {

    @Test
    fun ancestor() {
        tagSelector("div").ancestor()
            .xpathShouldBe("ancestor::div")
    }

    @Test
    fun ancestorOrSelf() {
        tagSelector("div").ancestorOrSelf()
            .xpathShouldBe("ancestor-or-self::div")
    }

    @Test
    fun child() {
        tagSelector("div").child()
            .xpathShouldBe("child::div")
    }

    @Test
    fun descendant() {
        tagSelector("div").descendant()
            .xpathShouldBe("descendant::div")
    }

    @Test
    fun descendantOrSelf() {
        tagSelector("div").descendantOrSelf()
            .xpathShouldBe("descendant-or-self::div")
    }

    @Test
    fun following() {
        tagSelector("div").following()
            .xpathShouldBe("following::div")
    }

    @Test
    fun followingSibling() {
        tagSelector("div").followingSibling()
            .xpathShouldBe("following-sibling::div")
    }

    @Test
    fun namespace() {
        tagSelector("div").namespace()
            .xpathShouldBe("namespace::div")
    }

    @Test
    fun parent() {
        tagSelector("div").parent()
            .xpathShouldBe("parent::div")
    }

    @Test
    fun preceding() {
        tagSelector("div").preceding()
            .xpathShouldBe("preceding::div")
    }

    @Test
    fun precedingSibling() {
        tagSelector("div").precedingSibling()
            .xpathShouldBe("preceding-sibling::div")
    }

    @Test
    fun self() {
        tagSelector("div").self()
            .xpathShouldBe("self::div")
    }
}