/*
 * Copyright (c) 2021 XPATH-QS
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.xpathqs.core.reflection

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.xpathqs.core.selector.args.SelectorArgs
import org.xpathqs.core.selector.extensions.core.get
import org.xpathqs.core.selector.selector.Selector
import org.xpathqs.core.util.SelectorFactory.tagSelector
import org.xpathqs.xpathShouldBe

internal class SelectorReflectionTest {

    @Test
    fun setName() {
        val s = Selector()
        SelectorReflection(s).setProp("name", "test_name")
        assertThat(s.name)
            .isEqualTo("test_name")
    }

    @Test
    fun setNameForObj() {
        SelectorReflection(PageWithBase).setProp("name", "test_name")
        assertThat(PageWithBase.name)
            .isEqualTo("test_name")
        assertThat(PageWithBase.toString())
            .isEqualTo("test_name")
    }

    @Test
    fun setArgsShouldUpdateArguments() {
        val sel = tagSelector("tag")[2].freeze()
        SelectorReflection(sel)
            .setArgs(SelectorArgs())

        sel
            .xpathShouldBe("//tag")
    }
}