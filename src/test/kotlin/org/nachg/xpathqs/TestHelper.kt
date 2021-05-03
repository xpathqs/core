package org.nachg.xpathqs

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.nachg.xpathqs.core.selector.ISelector

fun ISelector.xpathShouldBe(xpath: String) {
    assertThat(this.toXpath())
        .isEqualTo(xpath)
}