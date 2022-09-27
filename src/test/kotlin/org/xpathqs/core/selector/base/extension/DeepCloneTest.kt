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

package org.xpathqs.core.selector.base.extension

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeSameInstanceAs
import io.kotest.matchers.types.shouldNotBeSameInstanceAs
import org.xpathqs.core.reflection.parse
import org.xpathqs.core.selector.base.SelectorState
import org.xpathqs.core.selector.base.deepClone
import org.xpathqs.core.selector.block.Block
import org.xpathqs.core.util.SelectorFactory.tagSelector
import org.xpathqs.gwt.GIVEN

class DeepCloneTest : AnnotationSpec() {
    class Clone : Block() {
        val s = tagSelector("div")
    }

    /**
     * Require #1 check
     * @see [org.xpathqs.core.selector.base.deepClone]
     */
    @Test
    fun r1_deepCloneForInitState() {
        GIVEN {
            tagSelector("sel")
        }.WHEN {
            given.deepClone()
        }.THEN {
            given shouldBeSameInstanceAs actual
        }
    }

    /**
     * Require #2 check
     * @see [org.xpathqs.core.selector.base.deepClone]
     */
    @Test
    fun r2_deepCloneForFreezeSelector() {
        GIVEN {
            Clone().parse().s
        }.WHEN {
            given.deepClone()
        }.THEN {
            assertSoftly {
                given shouldNotBeSameInstanceAs actual
                given.name shouldBe actual.name
                given.base shouldBe actual.base
                given.annotations shouldBe actual.annotations
                given.field shouldBe actual.field
                given.props shouldBe actual.props
            }
        }
    }

    /**
     * Require #3 check
     * @see [org.xpathqs.core.selector.base.deepClone]
     */
    @Test
    fun r3_deepCloneShouldChangeState() {
        GIVEN {
            Clone().parse().s
        }.WHEN {
            given.deepClone().state
        }.THEN {
            SelectorState.CLONED
        }
    }
}