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

package org.xpathqs.core.selector.block.extensions

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.types.shouldBeSameInstanceAs
import io.kotest.matchers.types.shouldNotBeSameInstanceAs
import org.xpathqs.core.reflection.freeze
import org.xpathqs.core.reflection.parse
import org.xpathqs.core.selector.base.SelectorState
import org.xpathqs.core.selector.block.Block
import org.xpathqs.core.selector.block.deepClone
import org.xpathqs.core.selector.extensions.core.get
import org.xpathqs.core.util.SelectorFactory.tagSelector
import org.xpathqs.gwt.GIVEN
import org.xpathqs.stateShouldBe
import org.xpathqs.xpathShouldBe

class DeepCloneTest : AnnotationSpec() {
    open class BlockCls : Block() {
        val s1 = tagSelector("div")
    }

    open class BlockClsWithBase : Block(tagSelector("base")) {
        val s1 = tagSelector("div")
    }

    object Object : BlockCls()

    @Test
    fun r1_cloneOfBlockCls() {
        GIVEN {
            BlockCls().parse()
        }.WHEN {
            given.deepClone()
        }.THEN {
            actual shouldNotBeSameInstanceAs given
            actual.s1 shouldNotBeSameInstanceAs given.s1
            actual.s1.base shouldNotBeSameInstanceAs given.s1.base
            actual.s1.base shouldBeSameInstanceAs actual
        }
    }

    @Test
    fun r2_innerSelectorsState() {
        GIVEN {
            BlockCls().parse()
        }.WHEN {
            given.deepClone()
        }.THEN {
            actual.stateShouldBe(SelectorState.CLONED)
            actual.s1.stateShouldBe(SelectorState.CLONED)
        }
    }

    @Test
    fun r3_innerSelectors() {
        GIVEN {
            BlockCls().parse()
        }.WHEN {
            given.deepClone().freeze()
        }.THEN {
            given.s1[2]
                .xpathShouldBe("//div[position()=2]")
            actual.s1[3]
                .xpathShouldBe("//div[position()=3]")
            given.s1
                .xpathShouldBe("//div")
            actual.s1
                .xpathShouldBe("//div")
        }
    }
}