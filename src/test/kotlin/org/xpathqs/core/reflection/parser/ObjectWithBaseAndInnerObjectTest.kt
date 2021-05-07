package org.xpathqs.core.reflection.parser

import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.xpathqs.core.reflection.PageWithBaseAndInnerObject
import org.xpathqs.core.reflection.SelectorParser
import org.xpathqs.xpathShouldBe


internal class ObjectWithBaseAndInnerObjectTest {

    @BeforeEach
    fun before() {
        SelectorParser(PageWithBaseAndInnerObject).parse()
    }

    @Test
    fun checkInnerName() {
        assertThat(PageWithBaseAndInnerObject.Inner.name)
            .isEqualTo("PageWithBaseAndInnerObject.Inner")
    }

    @Test
    fun checkSelectorName() {
        assertThat(PageWithBaseAndInnerObject.Inner.s1_inner.name)
            .isEqualTo("PageWithBaseAndInnerObject.Inner.s1_inner")
    }

    @Test
    fun checkSelectorXpath() {
        PageWithBaseAndInnerObject.Inner.s1_inner
            .xpathShouldBe("//base//inner//inner_tag")
    }

    @Test
    fun checkPageChildren() {
        assertThat(PageWithBaseAndInnerObject.Inner.children)
            .hasSize(1)
    }
}