package org.nachg.xpathqs.core.selector.args.decorators

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.nachg.xpathqs.core.selector.args.KVSelectorArg

internal class CommaDecoratorTest {

    @Test
    fun toXpath() {
        assertThat(CommaDecorator(KVSelectorArg("text()", "text")).toXpath())
            .isEqualTo("text()='text'")
    }

    @Test
    fun toXpathWithComma() {
        assertThat(CommaDecorator(KVSelectorArg("@text", "don't")).toXpath())
            .isEqualTo("@text=concat('don',\"'\",'t')")
    }
}