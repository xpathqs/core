package org.xpathqs.core.selector.base

import assertk.assertAll
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotSameAs
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

    @Test
    fun clone() {
        val props = BaseSelectorProps(
            args = SelectorArgs(
                KVSelectorArg("@type", "'submit'")
            )
        )
        val cloned = props.clone()

        assertAll {
            assertThat(props)
                .isNotSameAs(cloned)

            assertThat(props.args)
                .isNotSameAs(cloned.args)
        }
    }

}