package org.xpathqs.core.selector.args.decorators

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.xpathqs.core.selector.args.KVSelectorArg

internal class ContainsDecoratorTest {

    @Test
    fun toXpath() {
        assertThat(ContainsDecorator(KVSelectorArg("text()", "text")).toXpath())
            .isEqualTo("contains(text(), text)")
    }
}