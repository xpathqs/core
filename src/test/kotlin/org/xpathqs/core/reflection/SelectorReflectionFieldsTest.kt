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

package org.xpathqs.core.reflection

import assertk.assertThat
import assertk.assertions.containsExactlyInAnyOrder
import org.junit.jupiter.api.Test
import org.xpathqs.core.selector.selector.Selector

internal class SelectorReflectionFieldsTest {

    private val selectorFields = arrayOf("state", "base", "props", "name")

    @Test
    fun declaredFieldsForSelector() {
        val s = Selector()
        val actual = SelectorReflectionFields(s).declaredFields
        val names = actual.map { it.name }

        assertThat(names)
            .containsExactlyInAnyOrder(
                *selectorFields
            )
    }

    @Test
    fun declaredFieldsForBlockObject() {
        val actual = SelectorReflectionFields(PageWithBase).declaredFields
        val names = actual.map { it.name }

        val expected = ArrayList<String>()
        expected.addAll(selectorFields)
        expected.add("s1")
        expected.add("isBlank")
        expected.add("originBlock")
        expected.add("children")
        expected.add("selectorsChain")

        assertThat(names)
            .containsExactlyInAnyOrder(
                *expected.toTypedArray()
            )
    }

    @Test
    fun innerSelectorFields() {
        val actual = SelectorReflectionFields(PageWithBase).innerSelectorFields
        val names = actual.map { it.name }

        assertThat(names)
            .containsExactlyInAnyOrder(
                "s1"
            )
    }

    @Test
    fun innerSelectorFieldsWithInnerObject() {
        val actual = SelectorReflectionFields(PageWithBaseAndInnerObject).innerSelectorFields
        val names = actual.map { it.name }

        assertThat(names)
            .containsExactlyInAnyOrder(
                "s1_base"
            )
    }

    @Test
    fun innerSelectorFieldsForInnerObject() {
        val actual = SelectorReflectionFields(PageWithBaseAndInnerObject.Inner).innerSelectorFields
        val names = actual.map { it.name }

        assertThat(names)
            .containsExactlyInAnyOrder(
                "s1_inner"
            )
    }

    @Test
    fun innerObjectClasses() {
        val actual = SelectorReflectionFields(PageWithBaseAndInnerObject).innerObjectClasses
        val names = actual.map { it.simpleName }

        assertThat(names)
            .containsExactlyInAnyOrder(
                "Inner"
            )
    }

    @Test
    fun innerBlocks() {
        val actual = SelectorReflectionFields(PageWithBaseAndInnerObject).innerBlocks

        assertThat(actual)
            .containsExactlyInAnyOrder(
                PageWithBaseAndInnerObject.Inner
            )
    }
}