package org.xpathqs.core.selector.selector

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.xpathqs.core.selector.extensions.prefix
import org.xpathqs.core.selector.extensions.tag
import org.xpathqs.core.util.SelectorFactory.tagSelector
import org.xpathqs.xpathShouldBe

internal class SelectorTest {

    @Test
    fun toXpath() = Selector().xpathShouldBe("//*")

    @Test
    fun toXpathWithCustomProps() = Selector(
        props = SelectorProps(
            prefix = "/",
            tag = "div"
        )
    ).xpathShouldBe("/div")

    @Test
    fun toXpathWithBase() = Selector(
        base = Selector(
            props = SelectorProps(
                prefix = "//",
                tag = "base"
            )
        )
    ).xpathShouldBe("//base//*")

    @Test
    fun prefix() {
        assertThat(tagSelector("div").prefix)
            .isEqualTo("//")

        assertThat(tagSelector("div").prefix("/").prefix)
            .isEqualTo("/")
    }

    @Test
    fun tag() {
        assertThat(tagSelector("div").tag)
            .isEqualTo("div")

        assertThat(tagSelector("div").tag("p").tag)
            .isEqualTo("p")
    }
}