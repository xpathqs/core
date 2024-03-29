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

package org.xpathqs.core.selector.block

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.types.shouldNotBeSameInstanceAs
import org.xpathqs.core.reflection.SomeHolder
import org.xpathqs.core.reflection.parse
import org.xpathqs.core.selector.base.BaseSelector
import org.xpathqs.core.selector.extensions.core.get
import org.xpathqs.core.selector.selector.Selector
import org.xpathqs.core.util.SelectorFactory.tagSelector
import org.xpathqs.xpathShouldBe

class BlockSelectorCloneTest : AnnotationSpec() {
    open class HolderWithArgsNoScan(
        base: Selector,
        val sel1: BaseSelector,
    ) : Block(base)

    class SomeTest : Block(tagSelector("div")) {
        val s1 = tagSelector("p")
    }

    object PageWithBlockMembers : Block(tagSelector("base")) {
        val holder1 = SomeHolder()
    }

    @Test
    fun test2() {
        val s1 = SomeTest().parse()
        val s2 = s1.deepClone()

        s1 shouldNotBeSameInstanceAs s2
    }

    @Test
    fun test() {
        val props1 = PageWithBlockMembers.holder1.props
        val props2 = PageWithBlockMembers.holder1.originFieldProps

        props1 shouldNotBeSameInstanceAs props2

        PageWithBlockMembers.holder1[2].sel1
            .xpathShouldBe("//base//hold[position()=2]//div")

        PageWithBlockMembers.holder1.sel1
            .xpathShouldBe("//base//hold//div")
    }

    @Test
    fun testXpathForSingleBlock() {
        val sel = Block(tagSelector("div")).parse()
        sel[2].xpathShouldBe("//div[position()=2]")
        sel.xpathShouldBe("//div")
    }


    init {
        PageWithBlockMembers.parse()
    }
}