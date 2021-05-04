package org.nachg.xpathqs.core.selector.extensions

import org.junit.jupiter.api.Test
import org.nachg.xpathqs.core.util.SelectorFactory.tagSelector
import org.nachg.xpathqs.xpathShouldBe

class TextModificationTests {

    @Test
    fun defaultText() {
        tagSelector("div").text("ok")
            .xpathShouldBe("//div[text()='ok']")
    }

    @Test
    fun defaultTextWithComma() {
        tagSelector("div").text("don't")
            .xpathShouldBe("//div[text()=concat('don',\"'\",'t')]")
    }

    @Test
    fun textNormalized() {
        tagSelector("div").text("ok", normalize = true)
            .xpathShouldBe("//div[text()=normalize-space('ok')]")
    }

    @Test
    fun textContains() {
        tagSelector("div").text("ok", contains = true)
            .xpathShouldBe("//div[contains(text(), 'ok')]")
    }

    @Test
    fun textContainsAndNormalized() {
        tagSelector("div").text("ok", contains = true, normalize = true)
            .xpathShouldBe("//div[contains(text(), normalize-space('ok'))]")
    }
}