package org.nachg.xpathqs.core.selector.extensions

import assertk.assertAll
import org.junit.jupiter.api.Test
import org.nachg.xpathqs.core.reflection.freeze
import org.nachg.xpathqs.core.selector.selector.Selector
import org.nachg.xpathqs.core.util.SelectorFactory
import org.nachg.xpathqs.xpathShouldBe

internal class PositionModificationTests {

    @Test
    fun getTestForSelector() {
        Selector()["position()=last()"]
            .xpathShouldBe("//*[position()=last()]")
    }

    @Test
    fun positionTestForSelector() {
        val s = SelectorFactory.tagSelector("div").freeze()

        assertAll {
            s[1].xpathShouldBe("//div[position()=1]")
            s[2].xpathShouldBe("//div[position()=2]")
            s.xpathShouldBe("//div")
        }
    }
}