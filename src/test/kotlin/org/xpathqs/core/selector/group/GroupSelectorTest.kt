package org.xpathqs.core.selector.group

import org.junit.jupiter.api.Test
import org.xpathqs.core.selector.extensions.plus
import org.xpathqs.core.selector.extensions.tag
import org.xpathqs.core.util.SelectorFactory.tagSelector
import org.xpathqs.xpathShouldBe

internal class GroupSelectorTest {

    @Test
    fun tagShouldNotBeUpdated() {
        val s = (tagSelector("div") + tagSelector("p"))
            .tag("a")
        s.xpathShouldBe("//div//p")
    }

    @Test
    fun chainOfMoreThanTwoElements() {
        (tagSelector("div") + tagSelector("div") + tagSelector("div"))
            .xpathShouldBe("//div//div//div")
    }

    @Test
    fun chainOfMoreThanTwoElementsWithGroup() {
        ((tagSelector("div") + tagSelector("div")) + tagSelector("div") + "//p")
            .xpathShouldBe("//div//div//div//p")
    }
}