package org.nachg.xpathqs.core.reflection.parser

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.nachg.xpathqs.core.reflection.Page_NoBase
import org.nachg.xpathqs.core.reflection.SelectorParser
import org.nachg.xpathqs.xpathShouldBe

internal class ObjectWithoutBase {

    @BeforeEach
    fun before() {
        SelectorParser(Page_NoBase).parse()
    }

    @Test
    fun checkSelectorXpathWithNoBase() {
        Page_NoBase.s1
            .xpathShouldBe("//s1")
    }
}