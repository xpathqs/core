package org.xpathqs.core.selector.args

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
                    ValueArg("last()")
                ).toXpath()
        ).isEqualTo("[last()]")
    }

    @Test
    fun toXpath_forAndArg() {
        assertThat(
            SelectorArgs()
                .add(
                    ValueArg("first()")
                ).add(
                    ValueArg("last()")
                ).toXpath()
        ).isEqualTo("[first() and last()]")
    }

    @Test
    fun toXpath_forOrArg() {
        assertThat(
            SelectorArgs()
                .add(
                    ValueArg("first()")
                ).add(
                    ValueArg("last()", JoinType.OR)
                ).toXpath()
        ).isEqualTo("[first() or last()]")
    }

    @Test
    fun toXpath_forOrAndArg() {
        assertThat(
            SelectorArgs()
                .add(
                    ValueArg("before()")
                )
                .add(
                    ValueArg("first()")
                ).add(
                    ValueArg("second()", JoinType.OR)
                ).add(
                    ValueArg("last()", JoinType.AND)
                ).toXpath()
        ).isEqualTo("[before() and first() and last() or second()]")
    }
}