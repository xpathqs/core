package org.xpathqs.core.selector.extensions

import assertk.assertThat
import assertk.assertions.isNotSameAs
import org.junit.jupiter.api.Test
import org.xpathqs.core.reflection.PageWithBaseWithChain
import org.xpathqs.core.reflection.PageWithBaseWithChainXpath
import org.xpathqs.core.reflection.SelectorParser
import org.xpathqs.core.reflection.freeze
import org.xpathqs.core.selector.selector.Selector
import org.xpathqs.xpathShouldBe

class SelectorCloneTests {

    @Test
    fun simpleClone() {
        val s1 = Selector().freeze()
        val s2 = s1.clone()

        assertThat(s1)
            .isNotSameAs(s2)
    }

    @Test
    fun cloneForBlockWithSelectorXpath() {
        SelectorParser(PageWithBaseWithChainXpath).parse()
        PageWithBaseWithChainXpath.s1
            .xpathShouldBe("//div//div//div//s1")
    }

    @Test
    fun cloneForBlockWithSelector() {
        SelectorParser(PageWithBaseWithChain).parse()
        PageWithBaseWithChain.s1
            .xpathShouldBe("//div//div//s1")
    }

}