package org.nachg.xpathqs.core.selector

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

import org.nachg.xpathqs.core.util.SelectorFactory.tagSelector

internal class SelectorFactoryTest {

    @Test
    fun tagSelector() {
        assertThat(tagSelector("div").toXpath())
            .isEqualTo("//div")
    }

}