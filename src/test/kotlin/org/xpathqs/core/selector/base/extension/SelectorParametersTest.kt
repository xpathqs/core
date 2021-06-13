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

package org.xpathqs.core.selector.base.extension

import org.junit.jupiter.api.Test
import org.xpathqs.core.selector.extensions.contains
import org.xpathqs.core.selector.extensions.containsAny
import org.xpathqs.core.selector.extensions.containsParent
import org.xpathqs.core.util.SelectorFactory.tagSelector
import org.xpathqs.core.util.SelectorFactory.textSelector
import org.xpathqs.core.util.SelectorFactory.xpathSelector
import org.xpathqs.gwt.WHEN
import org.xpathqs.xpathShouldBe

class SelectorParametersTest {

    /**
     * Checks #1 require
     * @see [org.xpathqs.core.selector.extensions.contains]
     */
    @Test
    fun r1_contains() {
        WHEN {
            tagSelector("div") contains tagSelector("p")
        }.ASSERT {
            actual
                .xpathShouldBe("//div[./p]")
        }
    }

    /**
     * Checks #1 require
     * @see [org.xpathqs.core.selector.extensions.containsAny]
     */
    @Test
    fun r1_containsAny() {
        WHEN {
            tagSelector("div") containsAny tagSelector("p")
        }.ASSERT {
            actual
                .xpathShouldBe("//div[.//p]")
        }
    }

    /**
     * Checks #2 require
     * @see [org.xpathqs.core.selector.extensions.containsAny]
     */
    @Test
    fun r2_containsAnyXpathWithPrefix() {
        WHEN {
            tagSelector("div") containsAny xpathSelector("./p")
        }.ASSERT {
            actual
                .xpathShouldBe("//div[./p]")
        }
    }

    /**
     * Checks #2 require
     * @see [org.xpathqs.core.selector.extensions.contains]
     */
    @Test
    fun r2_containsXpathWithPrefix() {
        WHEN {
            tagSelector("div") contains xpathSelector("./p")
        }.ASSERT {
            actual
                .xpathShouldBe("//div[./p]")
        }
    }
    /**
     * Checks #3 require
     * @see [org.xpathqs.core.selector.extensions.contains]
     */
    @Test
    fun r3_containsXpathWithoutPrefix() {
        WHEN {
            tagSelector("div") contains xpathSelector("p")
        }.ASSERT {
            actual
                .xpathShouldBe("//div[./p]")
        }
    }
    /**
     * Checks #1 require
     * @see [org.xpathqs.core.selector.extensions.containsParent]
     */
    @Test
    fun r1_containsParent() {
        WHEN {
            tagSelector("div") containsParent textSelector("some text")
        }.ASSERT {
            actual
                .xpathShouldBe("//div[../*[text()='some text']]")
        }
    }
    /**
     * Checks #2 require
     * @see [org.xpathqs.core.selector.extensions.containsParent]
     */
    @Test
    fun r2_containsParentXpathWithPrefix() {
        WHEN {
            tagSelector("div") containsParent xpathSelector("../p")
        }.ASSERT {
            actual
                .xpathShouldBe("//div[../p]")
        }
    }
    /**
     * Checks #3 require
     * @see [org.xpathqs.core.selector.extensions.containsParent]
     */
    @Test
    fun r3_containsParentXpathWithoutPrefix() {
        WHEN {
            tagSelector("div") containsParent xpathSelector("p")
        }.ASSERT {
            actual
                .xpathShouldBe("//div[../p]")
        }
    }
}