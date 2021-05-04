package org.nachg.xpathqs.core.reflection.parser

import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.nachg.xpathqs.core.reflection.Page_WithBase_AndInnerObject
import org.nachg.xpathqs.core.reflection.SelectorParser
import org.nachg.xpathqs.xpathShouldBe


internal class ObjectWithBaseAndInnerObjectTest {

    @BeforeEach
    fun before() {
        SelectorParser(Page_WithBase_AndInnerObject).parse()
    }

    @Test
    fun checkInnerName() {
        assertThat(Page_WithBase_AndInnerObject.Inner.name)
            .isEqualTo("Page_WithBase_AndInnerObject.Inner")
    }

    @Test
    fun checkSelectorName() {
        assertThat(Page_WithBase_AndInnerObject.Inner.s1_inner.name)
            .isEqualTo("Page_WithBase_AndInnerObject.Inner.s1_inner")
    }

    @Test
    fun checkSelectorXpath() {
        Page_WithBase_AndInnerObject.Inner.s1_inner
            .xpathShouldBe("//base//inner//inner_tag")
    }

    @Test
    fun checkPageChildren() {
        assertThat(Page_WithBase_AndInnerObject.Inner.children)
            .hasSize(1)
    }
}