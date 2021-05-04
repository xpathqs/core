package org.nachg.xpathqs.core.selector

import org.junit.jupiter.api.Test
import org.nachg.xpathqs.core.selector.extensions.get
import org.nachg.xpathqs.xpathShouldBe

internal class XpathSelectorTests {

    @Test
    fun toXpathWithoutProps() {
        XpathSelector("//div")
            .xpathShouldBe("//div")
    }

    @Test
    fun getTest() {
        XpathSelector("//div")["position()=last()"]
            .xpathShouldBe("//div[position()=last()]")
    }
}