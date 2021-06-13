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

package org.xpathqs.core.selector.group.extensions

import org.junit.jupiter.api.Test
import org.xpathqs.core.selector.args.SelectorArgs
import org.xpathqs.core.selector.args.ValueArg
import org.xpathqs.core.selector.base.BaseSelectorProps
import org.xpathqs.core.selector.extensions.plus
import org.xpathqs.core.selector.group.GroupSelector
import org.xpathqs.core.selector.group.addGroupArg
import org.xpathqs.core.util.SelectorFactory
import org.xpathqs.core.util.SelectorFactory.tagSelector
import org.xpathqs.gwt.GIVEN

class AddGroupArgTest {
    /**
     * Check #1 require
     * @see [org.xpathqs.core.selector.group.GroupSelector.addGroupArg]
     */
    @Test
    fun r1_singleSelector() {
        GIVEN {
            GroupSelector().add(
                tagSelector("div")
            )
        }.WHEN {
            given.addGroupArg(
                ValueArg("position()=2")
            ).toXpath()
        }.THEN {
            "//div[position()=2]"
        }
    }

    /**
     * Check #2 require
     * @see [org.xpathqs.core.selector.group.GroupSelector.addGroupArg]
     */
    @Test
    fun r2_multipleSelector() {
        GIVEN {
            tagSelector("div") + tagSelector("div")
        }.WHEN {
            given.addGroupArg(
                ValueArg("position()=2")
            ).toXpath()
        }.THEN {
            "(//div//div)[position()=2]"
        }
    }
}