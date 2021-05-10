package org.xpathqs.core.selector

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.xpathqs.core.constants.CoreGlobalProps

internal class CoreGlobalPropsTest {

    @Test
    fun checkDefaultTextProp() {
        assertThat(CoreGlobalProps().TEXT_ARG)
            .isEqualTo("text()")
    }

    @Test
    fun checkOverrideTextProp() {
        assertThat(
            CoreGlobalProps(
                mapOf("constants.text_arg" to "@text")
            ).TEXT_ARG
        )
            .isEqualTo("@text")
    }
}