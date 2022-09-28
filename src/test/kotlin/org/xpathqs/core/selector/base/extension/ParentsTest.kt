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
import org.xpathqs.core.reflection.scanPackage
import org.xpathqs.core.selector.block.Block
import org.xpathqs.core.selector.block.allInnerSelectorBlocks
import org.xpathqs.core.selector.extensions.*
import org.xpathqs.core.util.SelectorFactory.tagSelector
import org.xpathqs.gwt.WHEN

object Page1 : Block() {
    val s1 = tagSelector("div")

    object Inner : Block() {
        val s1 = tagSelector("div")
    }

    object Inner2: Block() {
        val s1 = tagSelector("div")

        object Inner3 : Block() {
            val s1 = tagSelector("div")
        }
    }
}

class ParentsTest : AnnotationSpec() {

    init {
        scanPackage(this)
    }

    /**
     * Checks Require #1 of [org.xpathqs.core.selector.base.BaseSelector.parents]
     */
    @Test
    fun r1_parents() {
        WHEN {
            tagSelector("div").parents
        }.THEN(
            emptyList()
        )
    }

    /**
     * Checks Require #2 of [org.xpathqs.core.selector.base.BaseSelector.parents]
     */
    @Test
    fun r2_parents() {
        WHEN {
            Page1.s1.parents
        }.THEN {
            arrayListOf(Page1)
        }
    }

    /**
     * Checks Require #3 of [org.xpathqs.core.selector.base.BaseSelector.parents]
     */
    @Test
    fun r3_parents() {
        WHEN {
            Page1.Inner.s1.parents
        }.THEN {
            arrayListOf(Page1.Inner, Page1)
        }
    }

    /**
     * Checks Require #1 of [org.xpathqs.core.selector.base.BaseSelector.isChildOf]
     */
    @Test
    fun r1_isChildOf() {
        WHEN {
            Page1.Inner isChildOf Page1
        }.THEN {
            true
        }
    }

    /**
     * Checks Require #2 of [org.xpathqs.core.selector.base.BaseSelector.isChildOf]
     */
    @Test
    fun r2_isChildOf() {
        WHEN {
            Page1 isChildOf Page1.Inner
        }.THEN {
            false
        }
    }

    /**
     * Checks Require #3 of [org.xpathqs.core.selector.base.BaseSelector.isChildOf]
     */
    @Test
    fun r3_isChildOf() {
        WHEN {
            Page1 isChildOf Page1
        }.THEN {
            false
        }
    }

    /**
     * Checks Require #1 of [org.xpathqs.core.selector.base.BaseSelector.isChildOf]
     */
    @Test
    fun r1_isChildOfForCollection() {
        WHEN {
            Page1.Inner2.Inner3 isChildOf Page1.allInnerSelectorBlocks
        }.THEN {
            true
        }
    }

    /**
     * Checks Require #2 of [org.xpathqs.core.selector.base.BaseSelector.isChildOf]
     */
    @Test
    fun r2_isChildOfForCollection() {
        WHEN {
            Page1.Inner isChildOf Page1.Inner2.allInnerSelectorBlocks
        }.THEN {
            false
        }
    }

    /**
     * Checks Require #1 of [org.xpathqs.core.selector.base.BaseSelector.isParentOf]
     */
    @Test
    fun r1_isParentOf() {
        WHEN {
            Page1 isParentOf Page1.Inner
        }.THEN {
            true
        }
    }

    /**
     * Checks Require #2 of [org.xpathqs.core.selector.base.BaseSelector.isParentOf]
     */
    @Test
    fun r2_isParentOf() {
        WHEN {
            Page1.Inner2 isParentOf Page1.Inner
        }.THEN {
            false
        }
    }

    /**
     * Checks Require #3 of [org.xpathqs.core.selector.base.BaseSelector.isParentOf]
     */
    @Test
    fun r3_isParentOf() {
        WHEN {
            Page1.Inner isParentOf Page1.Inner
        }.THEN {
            false
        }
    }

    /**
     * Checks Require #1 of [org.xpathqs.core.selector.base.BaseSelector.isParentOf]
     */
    @Test
    fun r1_isParentOfForCollections() {
        WHEN {
            Page1 isParentOf Page1.allInnerSelectorBlocks
        }.THEN {
            true
        }
    }

    /**
     * Checks Require #2 of [org.xpathqs.core.selector.base.BaseSelector.isParentOf]
     */
    @Test
    fun r2_isParentOfForCollections() {
        WHEN {
            Page1.Inner isParentOf Page1.Inner2.allInnerSelectorBlocks
        }.THEN {
            false
        }
    }

    /**
     * Checks [org.xpathqs.core.selector.base.BaseSelector.doesNotChildOf]
     */
    @Test
    fun doesNotChildOf() {
        WHEN {
            Page1.Inner doesNotChildOf Page1
        }.THEN {
            false
        }
    }

    /**
     * Checks [org.xpathqs.core.selector.base.BaseSelector.doesNotChildOf]
     */
    @Test
    fun doesNotChildOfForCollections() {
        WHEN {
            Page1.Inner doesNotChildOf Page1.allInnerSelectorBlocks
        }.THEN {
            true
        }
    }

    /**
     * Checks [org.xpathqs.core.selector.base.BaseSelector.doesNotParentOf]
     */
    @Test
    fun doesNotParentOf() {
        WHEN {
            Page1 doesNotParentOf Page1.Inner
        }.THEN {
            false
        }
    }

    /**
     * Checks [org.xpathqs.core.selector.base.BaseSelector.doesNotParentOf]
     */
    @Test
    fun doesNotParentOfForCollections() {
        WHEN {
            Page1 doesNotParentOf Page1.allInnerSelectorBlocks
        }.THEN {
            false
        }
    }
}