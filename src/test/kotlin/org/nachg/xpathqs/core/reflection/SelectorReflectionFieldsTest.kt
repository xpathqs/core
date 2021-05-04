package org.nachg.xpathqs.core.reflection

import assertk.assertThat
import assertk.assertions.containsExactlyInAnyOrder
import org.junit.jupiter.api.Test
import org.nachg.xpathqs.core.selector.Selector

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
        val actual = SelectorReflectionFields(Page_WithBase).declaredFields
        val names = actual.map { it.name }

        val expected = ArrayList<String>()
        expected.addAll(selectorFields)
        expected.add("s1")
        expected.add("isBlank")
        expected.add("originBlock")
        expected.add("children")

        assertThat(names)
            .containsExactlyInAnyOrder(
                *expected.toTypedArray()
            )
    }

    @Test
    fun innerSelectorFields() {
        val actual = SelectorReflectionFields(Page_WithBase).innerSelectorFields
        val names = actual.map { it.name }

        assertThat(names)
            .containsExactlyInAnyOrder(
                "s1"
            )
    }

    @Test
    fun innerSelectorFieldsWithInnerObject() {
        val actual = SelectorReflectionFields(Page_WithBase_AndInnerObject).innerSelectorFields
        val names = actual.map { it.name }

        assertThat(names)
            .containsExactlyInAnyOrder(
                "s1_base"
            )
    }

    @Test
    fun innerSelectorFieldsForInnerObject() {
        val actual = SelectorReflectionFields(Page_WithBase_AndInnerObject.Inner).innerSelectorFields
        val names = actual.map { it.name }

        assertThat(names)
            .containsExactlyInAnyOrder(
                "s1_inner"
            )
    }

    @Test
    fun innerObjectClasses() {
        val actual =  SelectorReflectionFields(Page_WithBase_AndInnerObject).innerObjectClasses
        val names = actual.map { it.simpleName }

        assertThat(names)
            .containsExactlyInAnyOrder(
                "Inner"
            )
    }


    @Test
    fun innerBlocks() {
        val actual = SelectorReflectionFields(Page_WithBase_AndInnerObject).innerBlocks

        assertThat(actual)
            .containsExactlyInAnyOrder(
                Page_WithBase_AndInnerObject.Inner
            )
    }
}