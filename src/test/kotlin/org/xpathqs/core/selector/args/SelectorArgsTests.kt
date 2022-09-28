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

package org.xpathqs.core.selector.args


import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import org.xpathqs.core.util.SelectorFactory.tagSelector
import org.xpathqs.gwt.WHEN


class SelectorArgsTests : AnnotationSpec() {

    @Test
    fun toXpath_ForNoArgs() {
        SelectorArgs().toXpath() shouldBe ""
    }

    @Test
    fun toXpath_forSingleArg() {
        SelectorArgs()
            .add(
                ValueArg("last()")
            ).toXpath() shouldBe "[last()]"
    }

    @Test
    fun toXpath_forAndArg() {
        SelectorArgs()
            .add(
                ValueArg("first()")
            ).add(
                ValueArg("last()")
            ).toXpath() shouldBe "[first() and last()]"
    }

    @Test
    fun toXpath_forOrArg() {
        SelectorArgs()
            .add(
                ValueArg("first()")
            ).add(
                ValueArg("last()", JoinType.OR)
            ).toXpath() shouldBe "[first() or last()]"
    }

    @Test
    fun toXpath_forOrAndArg() {
        SelectorArgs()
            .add(
                ValueArg("before()")
            )
            .add(
                ValueArg("first()")
            ).add(
                ValueArg("second()", JoinType.OR)
            ).add(
                ValueArg("last()", JoinType.AND)
            ).toXpath() shouldBe "[before() and first() and last() or second()]"
    }


    /**
     * Check require #1 of [SelectorArgs.add]
     */
    @Test
    fun addSelector_r1() {
        WHEN {
            SelectorArgs()
                .add(tagSelector("div"))
                .toXpath()
        }.THEN {
            "[//div]"
        }
    }
}
