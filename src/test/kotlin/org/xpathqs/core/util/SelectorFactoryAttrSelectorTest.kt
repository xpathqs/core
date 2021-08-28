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

package org.xpathqs.core.util

import org.junit.jupiter.api.Test
import org.xpathqs.core.util.SelectorFactory.attrSelector
import org.xpathqs.core.util.SelectorFactory.textWithInnerTagsSelector
import org.xpathqs.xpathShouldBe

class SelectorFactoryAttrSelectorTest {

    /**
     * Check require #1 of [SelectorFactory.attrSelector]
     */
    @Test
    fun withoutArgs() {
        attrSelector()
            .xpathShouldBe("//*[@*]")
    }

    /**
     * Check require #2 of [SelectorFactory.attrSelector]
     */
    @Test
    fun withName() {
        attrSelector(name="name")
            .xpathShouldBe("//*[@name]")
    }

    /**
     * Check require #3 of [SelectorFactory.attrSelector]
     */
    @Test
    fun withValue() {
        attrSelector(value="val")
            .xpathShouldBe("//*[@*='val']")
    }

    /**
     * Check require #4 of [SelectorFactory.attrSelector]
     */
    @Test
    fun withNameAndValue() {
        attrSelector(name="name", value="val")
            .xpathShouldBe("//*[@name='val']")
    }

    /**
     * Check require #5 of [SelectorFactory.attrSelector]
     */
    @Test
    fun withNameAndValueContains() {
        attrSelector(name="name", valueContains="val")
            .xpathShouldBe("//*[contains(@name, 'val')]")
    }

    /**
     * Check require #6 of [SelectorFactory.attrSelector]
     */
    @Test
    fun withValueContains() {
        attrSelector(valueContains="val")
            .xpathShouldBe("//*[contains(@*, 'val')]")
    }

    /**
     * Check require #1 of [textWithInnerTagsSelector]
     */
    @Test
    fun r1_textWithInnerTagsSelector() {
        textWithInnerTagsSelector("text1", "text2")
            .xpathShouldBe("//*[contains(text(), 'text1') and contains(., 'text2')]")
    }

    /**
     * Check require #2 of [textWithInnerTagsSelector]
     */
    @Test
    fun r2_textWithInnerTagsSelector() {
        textWithInnerTagsSelector("text1")
            .xpathShouldBe("//*[contains(text(), 'text1')]")
    }
}