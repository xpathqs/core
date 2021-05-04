package org.nachg.xpathqs.core.selector.compose

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.nachg.xpathqs.core.util.SelectorFactory.tagSelector

internal class ComposeSelectorPropsTests {

    @Test
    fun toXpathForEmpty() {
        assertThat(ComposeSelectorProps().toXpath())
            .isEqualTo("")
    }

    @Test
    fun toXpathForOne() {
        assertThat(
            ComposeSelectorProps(
                arrayListOf(
                    tagSelector("div")
                )
            ).toXpath()
        )
            .isEqualTo("//div")
    }

    @Test
    fun toXpathForMoreThanOne() {
        assertThat(
            ComposeSelectorProps(
                arrayListOf(
                    tagSelector("div"),
                    tagSelector("div")
                )
            ).toXpath()
        )
            .isEqualTo("(//div) | (//div)")
    }
}