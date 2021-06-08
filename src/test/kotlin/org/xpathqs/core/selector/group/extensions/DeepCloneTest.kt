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

package org.xpathqs.core.selector.group.extensions

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotSameAs
import org.junit.jupiter.api.Test
import org.xpathqs.core.reflection.freeze
import org.xpathqs.core.reflection.parse
import org.xpathqs.core.selector.block.Block
import org.xpathqs.core.selector.extensions.plus
import org.xpathqs.core.selector.group.GroupSelector
import org.xpathqs.core.selector.group.deepClone
import org.xpathqs.core.util.SelectorFactory.tagSelector
import org.xpathqs.gwt.GIVEN

class DeepCloneTest {
    class TestCls : Block(tagSelector("div")) {
        val s = tagSelector("s1") + tagSelector("s2")
    }

    /**
     * Check require #1 of [GroupSelector.deepClone]
     */
    @Test
    fun r1_parentPropsInit() {
        GIVEN {
            TestCls().parse().s
        }.WHEN {
            given.deepClone()
        }.ASSERT {
            assertThat(actual.name)
                .isEqualTo(given.name)

            assertThat(actual.base)
                .isEqualTo(given.base)
        }
    }

    /**
     * Check require #2 of [GroupSelector.deepClone]
     */
    @Test
    fun r2_selectorsChain() =
        GIVEN {
            tagSelector("div") + tagSelector("p")
        }.WHEN {
            given.freeze().deepClone()
        }.ASSERT {
            assertThat(actual)
                .isNotSameAs(given)

            assertThat(actual.selectorsChain)
                .isNotSameAs(given.selectorsChain)
            assertThat(actual.selectorsChain.size)
                .isEqualTo(actual.selectorsChain.size)

            assertThat(actual.selectorsChain.first())
                .isNotSameAs(given.selectorsChain.first())
            assertThat(actual.selectorsChain.first().toXpath())
                .isEqualTo(given.selectorsChain.first().toXpath())

            assertThat(actual.selectorsChain.last())
                .isNotSameAs(given.selectorsChain.last())
            assertThat(actual.selectorsChain.last().toXpath())
                .isEqualTo(given.selectorsChain.last().toXpath())
        }

    /**
     * Check require #3 of [GroupSelector.deepClone]
     */
    @Test
    fun r3_xpathEquals() =
        GIVEN {
            tagSelector("div") + tagSelector("p")
        }.WHEN {
            given.freeze().deepClone()
        }.ASSERT {
            assertThat(actual.toXpath())
                .isEqualTo(given.toXpath())
        }

    /**
     * Check require #4 of [GroupSelector.deepClone]
     */
    @Test
    fun r4_clonedModification() =
        GIVEN {
            tagSelector("div") + tagSelector("p")
        }.WHEN {
            given.freeze().deepClone().add(tagSelector("div"))
        }.ASSERT {
            assertThat(actual.toXpath())
                .isEqualTo("//div//p//div")

            assertThat(given.toXpath())
                .isEqualTo("//div//p")
        }
}