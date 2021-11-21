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

package org.xpathqs.core.model

import assertk.assertAll
import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.xpathqs.core.reflection.SelectorParser
import org.xpathqs.core.selector.block.Block
import org.xpathqs.core.util.SelectorFactory.tagSelector

data class TestModel(
    val name1: String = "",
    val name2: String = "",
    val unmapped: String = ""
)

object TestModelPage: Block() {
    val name1 = tagSelector("div")
    val name2 = tagSelector("p")
}

class XpathExtractor : ISelectorValueExtractor {
    override fun apply(assoc: ModelAssociation): String {
        return assoc.sel.toXpath()
    }
}

internal class ModelTest {

    @Test
    fun associationTest() {
        val res = Model(TestModel::class.java, TestModelPage).associations

        val expected = listOf(
            ModelAssociation(
                TestModelPage.name1,
                TestModel::name1
            ),
            ModelAssociation(
                TestModelPage.name2,
                TestModel::name2
            )
        )

        assertThat(expected)
            .isEqualTo(res)
    }

    @Test
    fun applyTest() {
        val model = TestModel()

        ValueSetter(
            Model(TestModel::class.java, TestModelPage).associations,
            XpathExtractor()
        ).init(model)

        assertAll {
            assertThat(model.name1)
                .isEqualTo("//div")

            assertThat(model.name2)
                .isEqualTo("//p")
        }
    }

    companion object {
        @BeforeAll
        @JvmStatic
        fun init() {
            SelectorParser(TestModelPage).parse()
        }
    }
}