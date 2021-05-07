package org.xpathqs.core.selector.args.decorators

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.xpathqs.core.selector.args.KVSelectorArg

internal class KVNormalizeSpaceDecoratorTest {
    @Test
    fun toXpath() {
        assertThat(KVNormalizeSpaceDecorator(KVSelectorArg("text()", "text")).toXpath())
            .isEqualTo("text()=normalize-space(text)")
    }
}