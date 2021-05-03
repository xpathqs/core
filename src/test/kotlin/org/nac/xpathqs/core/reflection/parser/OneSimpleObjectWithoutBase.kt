package org.nac.xpathqs.core.reflection.parser

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.nac.xpathqs.core.reflection.Page_NoBase
import org.nac.xpathqs.core.reflection.SelectorParser
import org.nac.xpathqs.xpathShouldBe

internal class OneSimpleObjectWithoutBase {

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