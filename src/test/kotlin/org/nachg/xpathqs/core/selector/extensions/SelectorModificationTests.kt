package org.nachg.xpathqs.core.selector.extensions

import assertk.assertAll
import org.junit.jupiter.api.Test
import org.nachg.xpathqs.core.reflection.freeze
import org.nachg.xpathqs.core.selector.Selector
import org.nachg.xpathqs.core.selector.tag
import org.nachg.xpathqs.xpathShouldBe

class SelectorModificationTests {

    @Test
    fun tagTest() {
        val s1 = Selector().freeze()
        val s2 = s1.tag("s2")

        assertAll {
            s1.xpathShouldBe("//*")
            s2.xpathShouldBe("//s2")
        }
    }

}