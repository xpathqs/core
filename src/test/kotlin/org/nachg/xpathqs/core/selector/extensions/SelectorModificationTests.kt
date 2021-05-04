package org.nachg.xpathqs.core.selector.extensions

import assertk.assertAll
import org.junit.jupiter.api.Test
import org.nachg.xpathqs.core.reflection.freeze
import org.nachg.xpathqs.core.selector.selector.Selector
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
    fun textAndPosition() {
        tagSelector("div").text("test")[2]
            .xpathShouldBe("//div[text()='test' and position()=2]")
    }
}