package org.xpathqs.core.reflection

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.xpathqs.core.selector.args.SelectorArgs
import org.xpathqs.core.selector.extensions.get
import org.xpathqs.core.selector.selector.Selector
import org.xpathqs.core.util.SelectorFactory.tagSelector
import org.xpathqs.xpathShouldBe

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
    fun setArgsShouldUpdateArguments() {
        val sel = tagSelector("tag")[2].freeze()
        SelectorReflection(sel)
            .setArgs(SelectorArgs())

        sel
            .xpathShouldBe("//tag")
    }

    @Test
    fun isObject() {
        assertThat(PageWithBase.isObject())
            .isEqualTo(true)
    }
}