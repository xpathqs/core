package org.nachg.xpathqs.core.reflection.parser

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.nachg.xpathqs.core.reflection.PageNoBase
import org.nachg.xpathqs.core.reflection.SelectorParser
import org.nachg.xpathqs.xpathShouldBe

internal class ObjectWithoutBase {

    @BeforeEach
    fun before() {
        SelectorParser(PageNoBase).parse()
    }

    @Test
    fun checkSelectorXpathWithNoBase() {
        PageNoBase.s1
            .xpathShouldBe("//s1")
    }
}