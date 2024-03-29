/*
 * Copyright (c) 2022 XPATH-QS
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

package org.xpathqs.core.selector.extensions

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.types.shouldNotBeSameInstanceAs
import org.xpathqs.core.reflection.PageWithBaseWithChain
import org.xpathqs.core.reflection.PageWithBaseWithChainXpath
import org.xpathqs.core.reflection.SelectorParser
import org.xpathqs.core.reflection.freeze
import org.xpathqs.core.selector.selector.Selector
import org.xpathqs.core.util.SelectorFactory.tagSelector
import org.xpathqs.xpathShouldBe
import org.xpathqs.core.selector.extensions.core.clone
import org.xpathqs.core.selector.extensions.core.get

class SelectorCloneTests : AnnotationSpec() {

    @Test
    fun simpleClone() {
        val s1 = Selector().freeze()
        val s2 = s1.clone()

        s1 shouldNotBeSameInstanceAs s2
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

    @Test
    fun cloneSelectorWithArgs() {
        val s1 = tagSelector("div")[1].freeze()
        val s2 = s1.clone().freeze()
        val s3 = s2[3]

        s1.xpathShouldBe("//div[position()=1]")
        s2.xpathShouldBe("//div[position()=1]")
        s3.xpathShouldBe("//div[position()=3]")
    }
}