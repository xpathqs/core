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
import org.xpathqs.core.reflection.parse
import org.xpathqs.core.selector.block.*
import org.xpathqs.core.util.SelectorFactory.tagSelector
import org.xpathqs.gwt.WHEN

class InnerSelectorsTest : AnnotationSpec() {

    object PageWithoutInner: Block() {
        val s1 = tagSelector("div")
        val s2 = tagSelector("div")
    }

    object PageWithInner: Block() {
        val s1 = tagSelector("div")
        val s2 = tagSelector("div")

        object Inner: Block() {
            val s1 = tagSelector("div")
            val s2 = tagSelector("div")
        }
    }

    object PageWithInner2: Block() {
        val s1 = tagSelector("div")
        val s2 = tagSelector("div")

        object Inner: Block() {
            val s1 = tagSelector("div")
            val s2 = tagSelector("div")

            object Inner2: Block() {
                val s1 = tagSelector("div")
                val s2 = tagSelector("div")

                object Inner3: Block() {
                    val s1 = tagSelector("div")
                    val s2 = tagSelector("div")

                    object Inner4: Block() {
                        val s1 = tagSelector("div")
                        val s2 = tagSelector("div")

                        object Inner5: Block() {
                            val s1 = tagSelector("div")
                            val s2 = tagSelector("div")
                        }
                    }
                }
            }
        }
    }

    object PageWithInnerClass: Block() {
        val s1 = tagSelector("div")
        val s2 = tagSelector("div")

        class InnerCls: Block() {
            val s1 = tagSelector("div")
            val s2 = tagSelector("div")
        }
        val Inner = InnerCls()
    }

    init {
        PageWithoutInner.parse()
        PageWithInner.parse()
        PageWithInnerClass.parse()
    }

    /**
     * Check require #1 of [PageWithoutInner.selectors]
     */
    @Test
    fun r1_selectors() {
        WHEN {
            PageWithoutInner.selectors
        }.THEN {
            arrayListOf(
                PageWithoutInner.s1,
                PageWithoutInner.s2
            )
        }
    }

    /**
     * Check require #2 of [PageWithoutInner.selectors]
     */
    @Test
    fun r2_selectors() {
        WHEN {
            PageWithInner.selectors
        }.THEN {
            arrayListOf(
                PageWithInner.s1,
                PageWithInner.s2
            )
        }
    }

    /**
     * Check require #1 of [PageWithoutInner.selectorBlocks]
     */
    @Test
    fun r1_selectorBlocks() {
        WHEN {
            PageWithoutInner.selectorBlocks
        }.THEN(
            arrayListOf<String>()
        )
    }

    /**
     * Check require #2 of [PageWithoutInner.selectorBlocks]
     */
    @Test
    fun r2_selectorBlocks() {
        WHEN {
            PageWithInner.selectorBlocks
        }.THEN {
            arrayListOf(
                PageWithInner.Inner
            )
        }
    }

    /**
     * Check require #3 of [PageWithoutInner.selectorBlocks]
     */
    @Test
    fun r3_selectorBlocks() {
        WHEN {
            PageWithInnerClass.selectorBlocks
        }.THEN {
            arrayListOf(
                PageWithInnerClass.Inner
            )
        }
    }

    /**
     * Check require #1 of [PageWithoutInner.allInnerSelectors]
     */
    @Test
    fun r1_allInnerSelectors() {
        WHEN {
            PageWithoutInner.allInnerSelectors
        }.THEN {
            arrayListOf(
                PageWithoutInner.s1,
                PageWithoutInner.s2,
            )
        }
    }

    /**
     * Check require #2 of [PageWithoutInner.allInnerSelectors]
     */
    @Test
    fun r2_allInnerSelectors() {
        WHEN {
            PageWithInner.allInnerSelectors
        }.THEN {
            arrayListOf(
                PageWithInner.s1,
                PageWithInner.s2,
                PageWithInner.Inner.s1,
                PageWithInner.Inner.s2
            )
        }
    }

    /**
     * Check require #3 of [PageWithoutInner.allInnerSelectors]
     */
    @Test
    fun r3_allInnerSelectors() {
        WHEN {
            PageWithInnerClass.allInnerSelectors
        }.THEN {
            arrayListOf(
                PageWithInnerClass.s1,
                PageWithInnerClass.s2,
                PageWithInnerClass.Inner,
                PageWithInnerClass.Inner.s1,
                PageWithInnerClass.Inner.s2
            )
        }
    }

    /**
     * Check require #2 of [PageWithoutInner.allInnerSelectorBlocks]
     */
    @Test
    fun r1_allInnerSelectorBlocks() {
        WHEN {
            PageWithInner2.allInnerSelectorBlocks
        }.THEN {
            arrayListOf(
                PageWithInner2.Inner,
                PageWithInner2.Inner.Inner2,
                PageWithInner2.Inner.Inner2.Inner3,
                PageWithInner2.Inner.Inner2.Inner3.Inner4,
                PageWithInner2.Inner.Inner2.Inner3.Inner4.Inner5,
            )
        }
    }

    /**
     * Check require #3 of [PageWithoutInner.allInnerSelectorBlocks]
     */
    @Test
    fun r2_allInnerSelectorBlocks() {
        WHEN {
            PageWithoutInner.allInnerSelectorBlocks
        }.THEN {
            emptyList<Block>()
        }
    }
}