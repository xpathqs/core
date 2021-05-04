package org.nachg.xpathqs.core.selector.extensions

import assertk.assertAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.nachg.xpathqs.core.reflection.Page_WithBase
import org.nachg.xpathqs.core.reflection.SelectorParser
import org.nachg.xpathqs.core.selector.tag
import org.nachg.xpathqs.xpathShouldBe

class SelectorObjectModificationTests {

    @BeforeEach
    fun before() {
        SelectorParser(Page_WithBase).parse()
    }

    @Test
    fun tagTest() {
        val s1 = Page_WithBase
        val s2 = Page_WithBase.tag("s2")

        assertAll {
            s1.xpathShouldBe("//base")
            s2.xpathShouldBe("//s2")
            s1.xpathShouldBe("//base")
        }
    }

    @Test
    fun tagTestForInnerSelector() {
        assertAll {
            Page_WithBase.tag("s2").s1.xpathShouldBe("//s2//s1")
            Page_WithBase.s1.xpathShouldBe("//base//s1")
        }
    }

    @Test
    fun tagTestForInnerSelectorWithUpdate() {
        val s = Page_WithBase.tag("s2").s1.tag("ss")

        assertAll {
            s.xpathShouldBe("//s2//ss")
            s.xpathShouldBe("//s2//ss")
        }
    }

}