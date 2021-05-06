package org.nachg.xpathqs.core.util

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.nachg.xpathqs.core.selector.CoreGlobalProps

internal class PropertyFacadeTest {

    val PATH = "config/config.yml"

    @Test

    fun parseWithInputStream() {
        assertThat(
            CoreGlobalProps(
                this::class.java.classLoader.getResource(PATH)?.openStream() ?:
                throw IllegalArgumentException("'$PATH' Resource can't be found")
            ).TEXT_ARG
        ).isEqualTo("@text")
    }

    @Test
    fun parseWithFacade() {
        assertThat(
            CoreGlobalProps(
                PropertyFacade(
                    this::class.java.classLoader.getResource(PATH)?.openStream() ?:
                        throw IllegalArgumentException("'$PATH' Resource can't be found")
                )
            ).TEXT_ARG
        ).isEqualTo("@text")
    }

}