package org.xpathqs.core.selector.extensions

import assertk.assertAll
import org.junit.jupiter.api.Test
import org.xpathqs.core.reflection.freeze
import org.xpathqs.core.selector.selector.Selector
import org.xpathqs.core.util.SelectorFactory.tagSelector
import org.xpathqs.xpathShouldBe

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
    fun onlyText() {
        tagSelector("div").text("test")
            .xpathShouldBe("//div[text()='test']")
    }

    @Test
    fun textAndPosition() {
        tagSelector("div").text("test")[2]
            .xpathShouldBe("//div[text()='test' and position()=2]")
    }

    @Test
    fun textContainsAndPosition() {
        tagSelector("div").text("test", contains = true)[2]
            .xpathShouldBe("//div[contains(text(), 'test') and position()=2]")
    }

    @Test
    fun removeParamsTest() {
        tagSelector("div")[2].removeParams()
            .xpathShouldBe("//div")
    }

    @Test
    fun prefixTest() {
        tagSelector("div").prefix("/")
            .xpathShouldBe("/div")
    }

    @Test
    fun repeatTest() {
        tagSelector("div").repeat(3)
            .xpathShouldBe("//div//div//div")
    }
}