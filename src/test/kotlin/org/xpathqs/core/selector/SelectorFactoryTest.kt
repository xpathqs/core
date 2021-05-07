package org.xpathqs.core.selector

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

import org.xpathqs.core.util.SelectorFactory.tagSelector

internal class SelectorFactoryTest {

    @Test
    fun tagSelector() {
        assertThat(tagSelector("div").toXpath())
            .isEqualTo("//div")
    }

}