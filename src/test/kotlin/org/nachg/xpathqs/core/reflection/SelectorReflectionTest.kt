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
        SelectorReflection(PageWithBase).setProp("name", "test_name")
        assertThat(PageWithBase.name)
            .isEqualTo("test_name")
        assertThat(PageWithBase.toString())
            .isEqualTo("test_name")
    }

    @Test
    fun isObject() {
        assertThat(PageWithBase.isObject())
            .isEqualTo(true)
    }
}