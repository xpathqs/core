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

package org.xpathqs.core.reflection.annotations

import assertk.assertThat
import assertk.assertions.isSameAs
import org.junit.jupiter.api.Test
import org.xpathqs.core.annotations.NoXpathBase
import org.xpathqs.core.reflection.parse
import org.xpathqs.core.selector.block.Block
import org.xpathqs.core.selector.extensions.plus
import org.xpathqs.core.selector.extensions.text
import org.xpathqs.core.util.SelectorFactory.tagSelector
import org.xpathqs.core.util.SelectorFactory.xpathSelector
import org.xpathqs.xpathShouldBe

class NoXpathBaseTest {

    object NoXpathBaseOjb : Block(tagSelector("baseSel")) {
        @NoXpathBase
        val sel = tagSelector("tag")

        @NoXpathBase
        val sel2 = tagSelector("tag") + tagSelector("tag2")

        @NoXpathBase
        val sel3 = xpathSelector("//tag")

        //tag
    }

    init {
        NoXpathBaseOjb.parse()
    }

    /**
     * Checks Require #1 of [NoXpathBase]
     */
    @Test
    fun r1() {
        NoXpathBaseOjb.sel.xpathShouldBe("//tag")
        assertThat(NoXpathBaseOjb.sel.base)
            .isSameAs(NoXpathBaseOjb)
    }

    /**
     * Checks Require #2 of [NoXpathBase]
     */
    @Test
    fun r2() {
        NoXpathBaseOjb.sel2.xpathShouldBe("//tag//tag2")
        assertThat(NoXpathBaseOjb.sel2.base)
            .isSameAs(NoXpathBaseOjb)
    }

    /**
     * Checks Require #3 of [NoXpathBase]
     */
    @Test
    fun r3() {
        NoXpathBaseOjb.sel.xpathShouldBe("//tag")
        assertThat(NoXpathBaseOjb.sel3.base)
            .isSameAs(NoXpathBaseOjb)
    }
}