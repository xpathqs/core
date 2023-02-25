/*
 * Copyright (c) 2023 XPATH-QS
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

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import org.xpathqs.core.selector.base.BaseSelector
import org.xpathqs.core.selector.selector.Selector

class SelectorReflectionFieldsTest : AnnotationSpec() {

    private val selectorFields = arrayOf(
        BaseSelector::state.name,
        BaseSelector::base.name,
        BaseSelector::props.name,
        BaseSelector::name.name,
        BaseSelector::fullName.name,
        BaseSelector::annotations.name,
        BaseSelector::property.name,
        BaseSelector::noBase.name,
        BaseSelector::customPropsMap.name,
        BaseSelector::tag.name,
        BaseSelector::xpath.name,
        "prefix"
    )

    @Test
    fun declaredFieldsForSelector() {
        val s = Selector()
        val actual = SelectorReflectionFields(s).declaredProps
        val names = actual.map { it.name }

        names shouldContainExactlyInAnyOrder selectorFields.toList()
    }

    @Test
    fun declaredFieldsForBlockObject() {
        val actual = SelectorReflectionFields(PageWithBase).declaredProps
        val names = actual.map { it.name } + "prefix"

        val expected = ArrayList<String>()
        expected.addAll(selectorFields)
        expected.add("s1")
        expected.add("isBlank")
        expected.add("originBlock")
        expected.add("children")
        expected.add("selectorsChain")
        expected.add("originFieldProps")

        names shouldContainExactlyInAnyOrder expected.toList()
    }

    @Test
    fun innerSelectorFields() {
        val actual = SelectorReflectionFields(PageWithBase).innerSelectorProps
        val names = actual.map { it.name }

        names shouldContainExactlyInAnyOrder listOf("s1", "originBlock")
    }

    @Test
    fun innerSelectorFieldsWithInnerObject() {
        val actual = SelectorReflectionFields(PageWithBaseAndInnerObject).innerSelectorProps
        val names = actual.map { it.name }

        names shouldContainExactlyInAnyOrder listOf("s1_base", "originBlock")
    }

    @Test
    fun innerSelectorFieldsWithInnerGroupObject() {
        SelectorParser(PageWithBaseAndInnerGroupObject).parse()

        val actual = SelectorReflectionFields(PageWithBaseAndInnerGroupObject).innerSelectorProps
        val names = actual.map {
            it.name
        }

        names shouldContainExactlyInAnyOrder listOf("s1_base", "originBlock")
    }

    @Test
    fun innerSelectorsWithInnerGroupObject() {
        SelectorParser(PageWithBaseAndInnerGroupObject).parse()
        val actual = SelectorReflectionFields(PageWithBaseAndInnerGroupObject).innerSelectors
        val names = actual.map { it.toXpath() }

        names shouldContainExactlyInAnyOrder listOf("//base//base_tag")
    }

    @Test
    fun innerSelectorFieldsForInnerObject() {
        val actual = SelectorReflectionFields(PageWithBaseAndInnerObject.Inner).innerSelectorProps
        val names = actual.map { it.name }

        names shouldContainExactlyInAnyOrder listOf("s1_inner", "originBlock")
    }

    @Test
    fun innerObjectClasses() {
        val actual = SelectorReflectionFields(PageWithBaseAndInnerObject).innerObjectClasses
        val names = actual.map { it.simpleName }

        names shouldContainExactlyInAnyOrder listOf("Inner")
    }

    @Test
    fun innerBlocks() {
        val actual = SelectorReflectionFields(PageWithBaseAndInnerObject).innerBlocks

        actual shouldContainExactlyInAnyOrder listOf(PageWithBaseAndInnerObject.Inner)
    }
}