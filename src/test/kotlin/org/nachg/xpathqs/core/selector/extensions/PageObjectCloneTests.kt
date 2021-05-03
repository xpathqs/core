package org.nachg.xpathqs.core.selector.extensions

import assertk.assertAll
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotSameAs
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.nachg.xpathqs.core.reflection.Page_WithBase
import org.nachg.xpathqs.core.reflection.SelectorParser
import org.nachg.xpathqs.core.selector.clone

class PageObjectCloneTests {
    @BeforeEach
    fun before() {
        SelectorParser(Page_WithBase).parse()
    }

    @Test
    fun checkClone() {
        val origin = Page_WithBase
        val cloned = Page_WithBase.clone()

        assertAll {
            assertThat(origin)
                .isNotSameAs(cloned)

            assertThat(origin.props)
                .isNotSameAs(cloned.props)

            assertThat(origin.children)
                .isNotSameAs(cloned.children)

            assertThat(origin.children.size)
                .isEqualTo(cloned.children.size)
        }
    }
}