package org.nachg.xpathqs.core.reflection

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.nachg.xpathqs.core.selector.selector.Selector

internal class SelectorReflectionTest {

    @Test
    fun setName() {
        val s = Selector()
        SelectorReflection(s).setProp("name", "test_name")
        assertThat(s.name)
            .isEqualTo("test_name")
    }

    @Test
    fun setNameForObj() {
        SelectorReflection(Page_WithBase).setProp("name", "test_name")
        assertThat(Page_WithBase.name)
            .isEqualTo("test_name")
        assertThat(Page_WithBase.toString())
            .isEqualTo("test_name")
    }

    @Test
    fun isObject() {
        assertThat(Page_WithBase.isObject())
            .isEqualTo(true)
    }
}