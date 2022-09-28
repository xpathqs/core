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

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.types.shouldNotBeSameInstanceAs
import org.xpathqs.core.selector.base.newInstance
import org.xpathqs.core.selector.block.Block
import org.xpathqs.core.selector.extensions.plus
import org.xpathqs.core.util.SelectorFactory.tagSelector
import org.xpathqs.core.util.SelectorFactory.xpathSelector
import org.xpathqs.gwt.GIVEN

class NewInstanceTest : AnnotationSpec() {
    class BlockCls : Block()
    object R1 : Block() {
        val m = BlockCls()
        val s = tagSelector("tag")
    }

    class R2Cls : Block() {
        val m = BlockCls()
    }

    class InnerMembers : Block() {
        inner class InnerCls : Block() {
            val m2 = tagSelector("div")
        }

        val m1 = InnerCls()
    }

    /**
     * Require #1 check
     * @see [org.xpathqs.core.selector.base.newInstance]
     */
    @Test
    fun r1_newInstanceForSelector() {
        GIVEN {
            tagSelector("div")
        }.WHEN {
            given.newInstance()
        }.THEN {
            given shouldNotBeSameInstanceAs actual
        }
    }

    /**
     * Require #2 check
     * @see [org.xpathqs.core.selector.base.newInstance]
     */
    @Test
    fun r2_newInstanceForXpathSelector() {
        GIVEN {
            xpathSelector("//div")
        }.WHEN {
            given.newInstance()
        }.THEN {
            given shouldNotBeSameInstanceAs actual
        }
    }

    /**
     * Require #3 check
     * @see [org.xpathqs.core.selector.base.newInstance]
     */
    @Test
    fun r3_newInstanceForGroupSelector() {
        GIVEN {
            tagSelector("div") + tagSelector("div")
        }.WHEN {
            given.newInstance()
        }.THEN {
            given shouldNotBeSameInstanceAs actual
        }
    }

    /**
     * Require #4 check
     * @see [org.xpathqs.core.selector.base.newInstance]
     */
    @Test
    fun r4_newInstanceForBlockSelector() {
        GIVEN {
            BlockCls()
        }.WHEN {
            given.newInstance()
        }.THEN {
            given shouldNotBeSameInstanceAs actual
        }
    }

    /**
     * Require #5 check
     * @see [org.xpathqs.core.selector.base.newInstance]
     */
    @Test
    fun r5_newInstanceForBlockSelectorWithBlockMembers() {
        GIVEN {
            R2Cls()
        }.WHEN {
            given.newInstance()
        }.THEN {
            given shouldNotBeSameInstanceAs actual
        }
    }

    /**
     * Require #6 check
     * @see [org.xpathqs.core.selector.base.newInstance]
     */
    @Test
    fun r6_newInstanceForBlockSelectorWithBlockInnerMembers() {
        GIVEN {
            InnerMembers().m1
        }.WHEN {
            given.newInstance()
        }.THEN {
            given shouldNotBeSameInstanceAs actual
        }
    }

    /**
     * Require #7 check
     * @see [org.xpathqs.core.selector.base.newInstance]
     */
    @Test
    fun r7_newInstanceForBlockObject() {
        GIVEN {
            R1
        }.WHEN {
            given.newInstance()
        }.THEN {
            given shouldNotBeSameInstanceAs actual
        }
    }
}