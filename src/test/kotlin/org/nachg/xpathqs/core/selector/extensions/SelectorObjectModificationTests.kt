package org.nachg.xpathqs.core.selector.extensions

import assertk.assertAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.nachg.xpathqs.core.reflection.PageWithBase
import org.nachg.xpathqs.core.reflection.SelectorParser
import org.nachg.xpathqs.xpathShouldBe

class SelectorObjectModificationTests {

    @BeforeEach
    fun before() {
        SelectorParser(PageWithBase).parse()
    }

    @Test
    fun tagTest() {
        val s1 = PageWithBase
        val s2 = PageWithBase.tag("s2")

        assertAll {
            s1.xpathShouldBe("//base")
            s2.xpathShouldBe("//s2")
            s1.xpathShouldBe("//base")
        }
    }

    @Test
    fun tagTestForInnerSelector() {
        assertAll {
            PageWithBase.tag("s2").s1.xpathShouldBe("//s2//s1")
            PageWithBase.s1.xpathShouldBe("//base//s1")
        }
    }

    @Test
    fun tagTestForInnerSelectorWithUpdate() {
        val s = PageWithBase.tag("s2").s1.tag("ss")

        assertAll {
            s.xpathShouldBe("//s2//ss")
            s.xpathShouldBe("//s2//ss")
        }
    }

}