package org.nac.xpathqs.core.selector

import assertk.assertThat
import assertk.assertions.isSameAs
import org.junit.jupiter.api.Test

import org.nac.xpathqs.xpathShouldBe

internal class NullSelectorTest {

    @Test
    fun toXpath() = NullSelector().xpathShouldBe("")

    @Test
    fun clone() {
        val s = NullSelector()
        assertThat(s)
            .isSameAs(s.clone())
    }
}