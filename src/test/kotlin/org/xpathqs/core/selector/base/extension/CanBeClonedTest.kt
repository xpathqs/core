/*
 * Copyright (c) 2021 Nikita A. Chegodaev
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

import org.junit.jupiter.api.Test
import org.xpathqs.core.reflection.parse
import org.xpathqs.core.selector.base.canBeCloned
import org.xpathqs.core.selector.block.Block
import org.xpathqs.core.util.SelectorFactory.tagSelector
import org.xpathqs.gwt.GIVEN

class CanBeClonedTest {
    class BlockCls : Block()
    object R1 : Block() {
        val m = BlockCls()
        val s = tagSelector("tag")
    }

    class R2Cls : Block() {
        val m = BlockCls()
    }

    /**
     * Require #1 check
     * @see [org.xpathqs.core.selector.base.canBeCloned]
     */
    @Test
    fun r1_canBeClonedForObject() =
        GIVEN {
            R1.parse()
        }.WHEN {
            R1.m.canBeCloned()
        }.THEN {
            false
        }

    /**
     * Require #2 check
     * @see [org.xpathqs.core.selector.base.canBeCloned]
     */
    @Test
    fun r2_canBeClonedForMember() =
        GIVEN {
            R2Cls().parse()
        }.WHEN {
            given.m.canBeCloned()
        }.THEN {
            true
        }
}