package org.nachg.xpathqs.core.selector

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

internal class KVSelectorArgTest {

    @Test
    fun toXpath() {
        assertThat(KVSelectorArg("position()", "1").toXpath())
            .isEqualTo("position()=1")
    }
}