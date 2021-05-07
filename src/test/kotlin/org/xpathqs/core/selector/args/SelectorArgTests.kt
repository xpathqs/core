package org.xpathqs.core.selector.args

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

internal class SelectorArgTests {

    @Test
    fun toXpath_forSingleArg() {
        assertThat(ValueArg("someVal").toXpath())
            .isEqualTo("someVal")
    }

    @Test
    fun toXpath_forAndArg() {
        assertThat(ValueArg("someVal", JoinType.AND).toXpath())
            .isEqualTo(" and someVal ")
    }

    @Test
    fun toXpath_forOrArg() {
        assertThat(ValueArg("someVal", JoinType.OR).toXpath())
            .isEqualTo(" or someVal ")
    }
}