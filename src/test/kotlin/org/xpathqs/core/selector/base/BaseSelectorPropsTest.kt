package org.xpathqs.core.selector.base

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.xpathqs.core.selector.args.KVSelectorArg
import org.xpathqs.core.selector.args.SelectorArgs

internal class BaseSelectorPropsTest {

    @Test
    fun toXpathWithoutSelectors() {
        assertThat(
            BaseSelectorProps(
                args = SelectorArgs(
                    KVSelectorArg("@type", "'submit'")
                )
            ).toXpath()
        ).isEqualTo("[@type='submit']")
    }
}