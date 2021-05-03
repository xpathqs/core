package org.nac.xpathqs.core.reflection

import assertk.assertThat
import assertk.assertions.containsExactlyInAnyOrder
import org.junit.jupiter.api.Test
import org.nac.xpathqs.core.selector.Selector

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

}