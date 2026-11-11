/*
 * Copyright (c) 2026 XPATH-QS
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

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.inspectors.forAll
import io.kotest.inspectors.forOne
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldNotBeSameInstanceAs
import org.xpathqs.core.reflection.freeze
import org.xpathqs.core.selector.NullSelector
import org.xpathqs.core.util.SelectorFactory.tagSelector

class ComposeSelectorPropsTests : AnnotationSpec() {

    @Test
    fun add() {
        ComposeSelectorProps(
            selectors = arrayListOf(
                tagSelector("div")
            )
        ).add(tagSelector("a"))
            .toXpath() shouldBe "(//div) | (//a)"
    }

    @Test
    fun clone() {
        val origin = ComposeSelectorProps(
            selectors = arrayListOf(
                tagSelector("div").freeze()
            )
        ).add(
            tagSelector("a").freeze()
        )

        val cloned = origin .clone()
        assertSoftly {
            cloned shouldNotBe origin
            cloned.selectors shouldHaveSize origin.selectors.size
            cloned.selectors shouldNotBeSameInstanceAs origin.selectors
            cloned.selectors.filter { it !is NullSelector }.forAll { c ->
                origin.selectors.forOne { o ->
                    c.name shouldBe o.name
                    c.xpath shouldBe o.xpath
                    c.fullName shouldBe o.fullName
                    c shouldNotBeSameInstanceAs o
                }
            }
        }
    }

    @Test
    fun toXpathForEmpty() {
        ComposeSelectorProps().toXpath() shouldBe ""
    }

    @Test
    fun toXpathForOne() {
        ComposeSelectorProps(
            arrayListOf(
                tagSelector("div")
            )
        ).toXpath() shouldBe "//div"
    }

    @Test
    fun toXpathForMoreThanOne() {
        ComposeSelectorProps(
            arrayListOf(
                tagSelector("div"),
                tagSelector("div")
            )
        ).toXpath() shouldBe "(//div) | (//div)"
    }
}