package org.nac.xpathqs.core.selector

import org.junit.jupiter.api.Test
import org.nac.xpathqs.xpathShouldBe

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

}