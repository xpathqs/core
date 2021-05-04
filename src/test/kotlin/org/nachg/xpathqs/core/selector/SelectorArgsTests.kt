package org.nachg.xpathqs.core.selector

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

internal class SelectorArgsTests {

    @Test
    fun toXpath_ForNoArgs() {
        assertThat(SelectorArgs().toXpath())
            .isEqualTo("")
    }

    @Test
    fun toXpath_forSingleArg() {
        assertThat(
            SelectorArgs()
                .add(
                    SelectorArg("last()")
                ).toXpath()
        )
            .isEqualTo("[last()]")
    }

    @Test
    fun toXpath_forAndArg() {
        assertThat(
            SelectorArgs()
                .add(
                    SelectorArg("first()")
                ).add(
                    SelectorArg("last()")
                ).toXpath()
        )
            .isEqualTo("[first() and last()]")
    }

    @Test
    fun toXpath_forOrArg() {
        assertThat(
            SelectorArgs()
                .add(
                    SelectorArg("first()")
                ).add(
                    SelectorArg("last()", JoinType.OR)
                ).toXpath()
        )
            .isEqualTo("[first() or last()]")
    }

    @Test
    fun toXpath_forOrAndArg() {
        assertThat(
            SelectorArgs()
                .add(
                    SelectorArg("first()")
                ).add(
                    SelectorArg("second()", JoinType.OR)
                ).add(
                    SelectorArg("last()", JoinType.AND)
                ).toXpath()
        )
            .isEqualTo("[first() or second() and last()]")
    }
}