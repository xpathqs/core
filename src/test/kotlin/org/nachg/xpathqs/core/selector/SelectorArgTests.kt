package org.nachg.xpathqs.core.selector

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.nachg.xpathqs.core.selector.args.JoinType
import org.nachg.xpathqs.core.selector.args.SelectorArg

internal class SelectorArgTests {

    @Test
    fun toXpath_forSingleArg() {
        assertThat(SelectorArg("someVal").toXpath())
            .isEqualTo("someVal")
    }

    @Test
    fun toXpath_forAndArg() {
        assertThat(SelectorArg("someVal", JoinType.AND).toXpath())
            .isEqualTo(" and someVal ")
    }

    @Test
    fun toXpath_forOrArg() {
        assertThat(SelectorArg("someVal", JoinType.OR).toXpath())
            .isEqualTo(" or someVal ")
    }
}