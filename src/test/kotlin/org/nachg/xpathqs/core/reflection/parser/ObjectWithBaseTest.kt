package org.nachg.xpathqs.core.reflection.parser

import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.nachg.xpathqs.core.reflection.PageWithBase
import org.nachg.xpathqs.core.reflection.SelectorParser
import org.nachg.xpathqs.xpathShouldBe


internal class ObjectWithBaseTest {

    @BeforeEach
    fun before() {
        SelectorParser(PageWithBase).parse()
    }

    @Test
    fun checkSelectorName() {
        assertThat(PageWithBase.s1.name)
            .isEqualTo("PageWithBase.s1")
    }

    @Test
    fun checkSelectorPageName() {
        assertThat(PageWithBase.name)
            .isEqualTo("PageWithBase")
    }

    @Test
    fun checkSelectorXpath() {
        PageWithBase.s1
            .xpathShouldBe("//base//s1")
    }

    @Test
    fun checkPageChildren() {
        assertThat(PageWithBase.children)
            .hasSize(1)
    }
}