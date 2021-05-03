package org.nac.xpathqs.core.reflection.parser

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.nac.xpathqs.core.reflection.Page_WithBase
import org.nac.xpathqs.core.reflection.SelectorParser
import org.nac.xpathqs.xpathShouldBe


internal class OneSimpleObjectWithBaseTest {

    @BeforeEach
    fun before() {
        SelectorParser(Page_WithBase).parse()
    }

    @Test
    fun checkSelectorName() {
        assertThat(Page_WithBase.s1.name)
            .isEqualTo("Page_WithBase.s1")
    }

    @Test
    fun checkSelectorPageName() {
        assertThat(Page_WithBase.name)
            .isEqualTo("Page_WithBase")
    }

    @Test
    fun checkSelectorXpath() {
       Page_WithBase.s1
           .xpathShouldBe("//base//s1")
    }
}