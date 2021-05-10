package org.xpathqs.core.selector

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.xpathqs.core.constants.CoreGlobalProps
import org.xpathqs.core.constants.Global

internal class CoreGlobalPropsTest {

    @Test
    fun checkTextPropForEmptyMap() {
        assertThat(CoreGlobalProps(emptyMap()).TEXT_ARG)
            .isEqualTo("text()")
    }

    @Test
    fun checkIdPropForEmptyMap() {
        assertThat(CoreGlobalProps(emptyMap()).ID_ARG)
            .isEqualTo("@id")
    }

    @Test
    fun checkDefaultTextProp() {
        assertThat(CoreGlobalProps().TEXT_ARG)
            .isEqualTo("text()")
    }

    @Test
    fun checkDefaultIdProp() {
        assertThat(CoreGlobalProps().ID_ARG)
            .isEqualTo("@id")
    }

    @Test
    fun checkDefaultIdPropForGlobal() {
        assertThat(Global.ID_ARG)
            .isEqualTo("@id")
    }

    @Test
    fun checkDefaultTextPropForGlobal() {
        assertThat(Global.TEXT_ARG)
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

    @Test
    fun checkUpdateOfAProperty() {
        assertThat(
            CoreGlobalProps(
                mapOf("constants.text_arg" to "@text")
            ).update(
                CoreGlobalProps(
                    mapOf("constants.text_arg" to "text()")
                )
            ).TEXT_ARG
        )
            .isEqualTo("text()")
    }

    @Test
    fun checkAbilityToLoadFromResources() {
        assertThat(
            CoreGlobalProps(
                "config/config.yml"
            ).TEXT_ARG
        )
            .isEqualTo("@text_test")
    }

    @Test
    fun incorrectPathShouldThrowAnException() {
        assertThrows<IllegalArgumentException> {
            CoreGlobalProps(
                "config/config_incorrect.yml"
            ).TEXT_ARG
        }
    }
}