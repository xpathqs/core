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

package org.xpathqs.core.reflection.parser

import assertk.assertAll
import assertk.assertThat
import assertk.assertions.isNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.xpathqs.core.reflection.PageWithBlockMembers
import org.xpathqs.core.reflection.SelectorParser
import org.xpathqs.core.selector.extensions.core.get
import org.xpathqs.nameShouldBe
import org.xpathqs.xpathShouldBe


class ObjectWithClassBlockTest {
    @BeforeEach
    fun parse() {
        SelectorParser(PageWithBlockMembers).parse()
    }

    @Test
    fun testSelectorFromClass() {
        assertAll {
            PageWithBlockMembers.holder1.sel1
                .xpathShouldBe("//base//hold//div")
                .nameShouldBe("PageWithBlockMembers.SomeHolder.sel1")
        }
    }

    @Test
    fun testSelectorFromClassWithPos() {
        assertAll {
            PageWithBlockMembers.holder1.sel1[2]
                .xpathShouldBe("//base//hold//div[position()=2]")
        }
    }

    @Test
    fun testSelectorFromClassWithBasePos() {
        assertAll {
            PageWithBlockMembers.holder1[2].sel1
                .xpathShouldBe("//base//hold[position()=2]//div")

            PageWithBlockMembers.holder1.sel1
                .xpathShouldBe("//base//hold//div")
        }
    }

    @Test
    fun fieldsForSelectors() {
        assertThat(PageWithBlockMembers.holder1.field)
            .isNotNull()
    }

    @Test
    fun fieldsForInnerSelectors() {
        assertThat(PageWithBlockMembers.holder1.sel1.field)
            .isNotNull()
    }
}