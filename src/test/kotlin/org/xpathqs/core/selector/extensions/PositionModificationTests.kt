package org.xpathqs.core.selector.extensions

import assertk.assertAll
import org.junit.jupiter.api.Test
import org.xpathqs.core.reflection.freeze
import org.xpathqs.core.selector.selector.Selector
import org.xpathqs.core.util.SelectorFactory
import org.xpathqs.xpathShouldBe

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