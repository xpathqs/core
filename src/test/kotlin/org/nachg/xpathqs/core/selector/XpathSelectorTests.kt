package org.nachg.xpathqs.core.selector

import org.junit.jupiter.api.Test
import org.nachg.xpathqs.xpathShouldBe

internal class XpathSelectorTests {
    @Test
    fun toXpathWithoutProps() {
        XpathSelector("//div")
            .xpathShouldBe("//div")
    }
}