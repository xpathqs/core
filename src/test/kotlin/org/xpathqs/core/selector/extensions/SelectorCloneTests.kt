package org.xpathqs.core.selector.extensions

import assertk.assertThat
import assertk.assertions.isNotSameAs
import org.junit.jupiter.api.Test
import org.xpathqs.core.reflection.freeze
import org.xpathqs.core.selector.selector.Selector

class SelectorCloneTests {

    @Test
    fun simpleClone() {
        val s1 = Selector().freeze()
        val s2 = s1.clone()

        assertThat(s1)
            .isNotSameAs(s2)
    }

}