package org.nachg.xpathqs.core.selector.extensions

import assertk.assertAll
import org.junit.jupiter.api.Test
import org.nachg.xpathqs.core.reflection.freeze
import org.nachg.xpathqs.core.selector.get
import org.nachg.xpathqs.core.selector.selector.Selector
import org.nachg.xpathqs.core.selector.tag
import org.nachg.xpathqs.core.util.SelectorFactory.tagSelector
import org.nachg.xpathqs.xpathShouldBe

class SelectorModificationTests {

    @Test
    fun tagTest() {
        val s1 = Selector().freeze()
        val s2 = s1.tag("s2")

        assertAll {
            s1.xpathShouldBe("//*")
            s2.xpathShouldBe("//s2")
        }
    }

    @Test
    fun getTestForSelector() {
        Selector()["position()=last()"]
            .xpathShouldBe("//*[position()=last()]")
    }

    @Test
    fun positionTestForSelector() {
        val s = tagSelector("div").freeze()

        assertAll {
            s[1].xpathShouldBe("//div[position()=1]")
            s[2].xpathShouldBe("//div[position()=2]")
            s.xpathShouldBe("//div")
        }
    }
}