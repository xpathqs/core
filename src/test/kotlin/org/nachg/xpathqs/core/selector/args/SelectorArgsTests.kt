package org.nachg.xpathqs.core.selector.args

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.nachg.xpathqs.core.selector.args.JoinType
import org.nachg.xpathqs.core.selector.args.SelectorArgs
import org.nachg.xpathqs.core.selector.args.ValueArg

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
                    ValueArg("first()")
                ).add(
                    ValueArg("second()", JoinType.OR)
                ).add(
                    ValueArg("last()", JoinType.AND)
                ).toXpath()
        ).isEqualTo("[first() or second() and last()]")
    }
}