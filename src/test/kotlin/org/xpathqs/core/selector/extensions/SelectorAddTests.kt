package org.xpathqs.core.selector.extensions

import assertk.assertAll
import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.xpathqs.core.util.SelectorFactory.tagSelector
import org.xpathqs.core.util.SelectorFactory.xpathSelector
import org.xpathqs.xpathShouldBe

internal class SelectorAddTests {
    @Test
    fun combineTwoTagSelectors() {
        (tagSelector("div") + tagSelector("p"))
            .xpathShouldBe("//div//p")
    }

    @Test
    fun combineTwoTagSelectorsWithArgs() {
        (tagSelector("div")[2] + tagSelector("p")[3])
            .xpathShouldBe("//div[position()=2]//p[position()=3]")
    }

    @Test
    fun addXpathSelector() {
        val firstSel = xpathSelector("//div")
        val secondSel = xpathSelector("//p")

        val combinedSelector = firstSel + secondSel

        assertAll {
            assertThat(combinedSelector.selectorsChain.size)
                .isEqualTo(2)

            combinedSelector.xpathShouldBe("//div//p")
        }
    }
}