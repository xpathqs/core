package org.nac.xpathqs

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.nac.xpathqs.core.selector.ISelector

fun ISelector.xpathShouldBe(xpath: String) {
    assertThat(this.toXpath())
        .isEqualTo(xpath)
}