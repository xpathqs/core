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

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.xpathqs.core.selector.base.SelectorState
import org.xpathqs.core.util.SelectorFactory.tagSelector
import org.xpathqs.stateShouldBe
import org.xpathqs.xpathShouldBe

internal class SelectorTest {

    @Test
    fun toXpath() = Selector().xpathShouldBe("//*")

    @Test
    fun defaultState() = Selector().stateShouldBe(SelectorState.INIT)

    @Test
    fun toXpathWithCustomProps() = Selector(
        props = SelectorProps(
            prefix = "/",
            tag = "div"
        )
    ).xpathShouldBe("/div")

    @Test
    fun toXpathWithBase() = Selector(
        base = Selector(
            props = SelectorProps(
                prefix = "//",
                tag = "base"
            )
        )
    ).xpathShouldBe("//base//*")

    @Test
    fun prefix() {
        assertThat(tagSelector("div").prefix)
            .isEqualTo("//")

        assertThat(tagSelector("div").prefix("/").prefix)
            .isEqualTo("/")
    }

    @Test
    fun tag() {
        assertThat(tagSelector("div").tag)
            .isEqualTo("div")

        assertThat(tagSelector("div").tag("p").tag)
            .isEqualTo("p")
    }
}