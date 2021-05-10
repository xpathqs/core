package org.xpathqs.core.selector

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.xpathqs.core.util.SelectorFactory.idContainsSelector
import org.xpathqs.core.util.SelectorFactory.idSelector
import org.xpathqs.core.util.SelectorFactory.tagSelector
import org.xpathqs.core.util.SelectorFactory.textContainsSelector
import org.xpathqs.core.util.SelectorFactory.textSelector
import org.xpathqs.xpathShouldBe

internal class SelectorFactoryTest {

    @Test
    fun tagSelector() {
        assertThat(tagSelector("div").toXpath())
            .isEqualTo("//div")
    }

    @Test
    fun textSelector() {
        assertThat(textSelector("div").toXpath())
            .isEqualTo("//*[text()='div']")
    }

    @Test
    fun textContainsSelector() {
        assertThat(textContainsSelector("some text").toXpath())
            .isEqualTo("//*[contains(text(), 'some text')]")
    }

    @Test
    fun idSelector() {
        idSelector("id")
            .xpathShouldBe("//*[@id='id']")
    }

    @Test
    fun idContainsSelector() {
        idContainsSelector("id")
            .xpathShouldBe("//*[contains(@id, 'id')]")
    }
}