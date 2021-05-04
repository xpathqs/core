package org.nachg.xpathqs.core.selector.args

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.nachg.xpathqs.core.selector.args.KVSelectorArg

internal class KVSelectorArgTest {

    @Test
    fun toXpath() {
        assertThat(KVSelectorArg("position()", "1").toXpath())
            .isEqualTo("position()=1")
    }
}