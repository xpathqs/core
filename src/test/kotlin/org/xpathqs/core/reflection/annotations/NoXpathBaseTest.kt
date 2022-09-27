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

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.types.shouldBeSameInstanceAs
import org.xpathqs.core.annotations.NoXpathBase
import org.xpathqs.core.reflection.parse
import org.xpathqs.core.selector.block.Block
import org.xpathqs.core.selector.extensions.plus
import org.xpathqs.core.util.SelectorFactory.tagSelector
import org.xpathqs.core.util.SelectorFactory.xpathSelector
import org.xpathqs.xpathShouldBe

class NoXpathBaseTest : FreeSpec() {

    object NoXpathBaseOjb : Block(tagSelector("baseSel")) {
        @NoXpathBase
        val baseSelector = tagSelector("tag")

        @NoXpathBase
        val composeSelector = tagSelector("tag") + tagSelector("tag2")

        @NoXpathBase
        val xpathSelector = xpathSelector("//tag")
    }

    init {
        NoXpathBaseOjb.parse()
        noXpathBaseTests()
    }

    /**
     * Checks Require #1 of [NoXpathBase]
     */
    fun noXpathBaseTests() {
        "Check requirements of 'NoXpathBase'" - {
            "#1 Annotated selector should ignore base xpath for [BaseSelector]" {
                assertSoftly(NoXpathBaseOjb.baseSelector) {
                    xpathShouldBe("//tag")
                    base shouldBeSameInstanceAs NoXpathBaseOjb
                }
            }
            "#2 Annotated selector should ignore base xpath for [GroupSelector]" {
                assertSoftly(NoXpathBaseOjb.composeSelector) {
                    xpathShouldBe("//tag//tag2")
                    base shouldBeSameInstanceAs NoXpathBaseOjb
                }
            }
            "#3 Annotated selector should ignore base xpath for [XpathSelector]" {
                assertSoftly(NoXpathBaseOjb.xpathSelector) {
                    xpathShouldBe("//tag")
                    base shouldBeSameInstanceAs NoXpathBaseOjb
                }
            }
        }
    }
}