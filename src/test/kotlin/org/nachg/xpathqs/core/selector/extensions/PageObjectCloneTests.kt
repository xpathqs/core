package org.nachg.xpathqs.core.selector.extensions

import assertk.assertAll
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotSameAs
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.nachg.xpathqs.core.reflection.PageWithBase
import org.nachg.xpathqs.core.reflection.SelectorParser

class PageObjectCloneTests {
    @BeforeEach
    fun before() {
        SelectorParser(PageWithBase).parse()
    }

    @Test
    fun checkClone() {
        val origin = PageWithBase
        val cloned = PageWithBase.clone()

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