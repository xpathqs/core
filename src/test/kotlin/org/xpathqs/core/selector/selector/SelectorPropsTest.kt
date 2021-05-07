package org.xpathqs.core.selector.selector

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.xpathqs.core.selector.args.SelectorArgs
import org.xpathqs.core.selector.args.ValueArg

class SelectorPropsTest {
    @Test
    fun toXpath() {
        assertThat(
            SelectorProps(
                prefix = "/",
                tag = "div",
                args = SelectorArgs(
                    args = hashMapOf("text()" to ValueArg("text()='asd'"))
                )
            ).toXpath()
        ).isEqualTo("/div[text()='asd']")
    }
}