package org.xpathqs.core.selector.compose

import org.junit.jupiter.api.Test
import org.xpathqs.core.selector.extensions.div
import org.xpathqs.core.selector.extensions.get
import org.xpathqs.core.util.SelectorFactory.compose
import org.xpathqs.core.util.SelectorFactory.tagSelector
import org.xpathqs.xpathShouldBe

class ComposeSelectorTests {

    @Test
    fun composeFuncWithNoArgs() {
        compose()
            .xpathShouldBe("")
    }

    @Test
    fun composeFuncWithOneArgs() {
        compose(tagSelector("div"))
            .xpathShouldBe("//div")
    }

    @Test
    fun composeFuncWithTwoArgs() {
        compose(tagSelector("div"), tagSelector("div"))
            .xpathShouldBe("(//div) | (//div)")
    }

    @Test
    fun composeFuncWithTwoArgsAndPos() {
        compose(tagSelector("div"), tagSelector("div"))[2]
            .xpathShouldBe("((//div) | (//div))[position()=2]")
    }

    @Test
    fun divOperator() {
        val s = tagSelector("div") / tagSelector("div")
        s.xpathShouldBe("(//div) | (//div)")
    }

    @Test
    fun divOperatorPriority() {
        val s = tagSelector("div")[2] / tagSelector("div")[3]
        s.xpathShouldBe("(//div[position()=2]) | (//div[position()=3])")
    }
}