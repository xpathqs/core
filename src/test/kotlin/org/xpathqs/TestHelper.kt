package org.xpathqs

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.xpathqs.core.selector.base.ISelector

fun ISelector.xpathShouldBe(xpath: String) {
    assertThat(this.toXpath())
        .isEqualTo(xpath)
}