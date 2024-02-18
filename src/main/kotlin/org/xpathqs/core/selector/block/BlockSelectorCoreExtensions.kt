/*
 * Copyright (c) 2024 XPATH-QS
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

package org.xpathqs.core.selector.block

import org.xpathqs.core.reflection.SelectorReflectionFields
import org.xpathqs.core.reflection.isObject
import org.xpathqs.core.reflection.setBase
import org.xpathqs.core.reflection.setBlank
import org.xpathqs.core.selector.base.BaseSelector
import org.xpathqs.core.selector.base.hasAnnotation
import org.xpathqs.core.selector.extensions.core.clone
import org.xpathqs.core.selector.group.GroupSelector
import org.xpathqs.core.selector.group.deepClone
import kotlin.reflect.KClass
import kotlin.reflect.jvm.javaField

/**
 * @return clone of a [Block] Selector
 *
 */
@Suppress("UNCHECKED_CAST")
internal fun <T : Block> T.deepClone(): T {
    val cloned = (this as GroupSelector).deepClone() as T
    cloned.setBlank(this.isBlank)

    if (this.isObject()) {
        cloned.originBlock = this
        cloned.originFieldProps = this.props.clone()
    }

    if (!this.isObject()) {
        cloned.children = this.children.map {
            val f = it.clone()
            it.property?.javaField?.set(cloned, f)
            f.setBase(cloned)
            f
        }
    } else {
        this.children.forEach {
            (it.property?.call(cloned) as BaseSelector).setBase(cloned)
            it.setBase(cloned)
        }
        cloned.children = this.children
    }
    return cloned
}

/**
 * @return Block's selectors without inner blocks
 *
 * Require #1 - all inner [BaseSelector]s of the [Block] should be returned
 * @sample [org.xpathqs.core.selector.block.extensions.InnerSelectorsTest.r1_selectors]
 *
 * Require #2 - all inner [BaseSelector]s of the inner [Block]s should be ignored
 * @sample [org.xpathqs.core.selector.block.extensions.InnerSelectorsTest.r2_selectors]
 */
val <T : Block> T.selectors: Collection<BaseSelector>
    get() {
        return this.children.filter {
            it !is Block
        }
    }

/**
 * @return Block's selectors inner blocks
 *
 * Require #1 - when there is no inner [Block]s selectors then emptyList should be returned
 * @sample [org.xpathqs.core.selector.block.extensions.InnerSelectorsTest.r1_selectorBlocks]
 *
 * Require #2 - inner [Block]s list should be returned
 * @sample [org.xpathqs.core.selector.block.extensions.InnerSelectorsTest.r2_selectorBlocks]
 *
 * Require #3 - all other selectors except [Block]s should be ignored
 * @sample [org.xpathqs.core.selector.block.extensions.InnerSelectorsTest.r3_selectorBlocks]
 */
val <T : Block> T.selectorBlocks: Collection<Block>
    get() {
        val res = ArrayList<Block>()
        res.addAll(SelectorReflectionFields(this).innerBlocks)
        res.addAll(this.children.filterIsInstance<Block>())
        return res
    }

/**
 * @return all inner Selector's block
 *
 * Require #1 - All inner [Block]s should be included
 * @sample [org.xpathqs.core.selector.block.extensions.InnerSelectorsTest.r1_allInnerSelectorBlocks]
 *
 * Require #2 - Empty result when there is no inners [Block]s
 * @sample [org.xpathqs.core.selector.block.extensions.InnerSelectorsTest.r2_allInnerSelectorBlocks]
 */
val <T : Block> T.allInnerSelectorBlocks: Collection<Block>
    get() {
        val res = ArrayList<Block>()
        selectorBlocks.forEach {
            res.add(it)
            res.addAll(it.allInnerSelectorBlocks)
        }
        return res
    }

/**
 * @return Block's selectors and inner selectors recursively for all inner blocks
 *
 * Require #1 - when there is no inner [Block]s selectors then [Block]s selectors should be returned
 * @sample [org.xpathqs.core.selector.block.extensions.InnerSelectorsTest.r1_allInnerSelectors]
 *
 * Require #2 - [Block]s selectors and inner [Block]s selectors should be returned
 * @sample [org.xpathqs.core.selector.block.extensions.InnerSelectorsTest.r2_allInnerSelectors]
 *
 * Require #3 - [Block]s selectors and inner [Block]s selectors should be returned for the block members
 * @sample [org.xpathqs.core.selector.block.extensions.InnerSelectorsTest.r3_allInnerSelectors]
 */
val <T : Block> T.allInnerSelectors: Collection<BaseSelector>
    get() {
        val res = ArrayList<BaseSelector>()
        res.addAll(children)
        selectorBlocks.forEach { block ->
            (block as? Block)?.allInnerSelectors?.let {
                res.addAll(it)
            }
        }
        return res
    }

fun <T: Block> T.findWithAnnotation(ann: KClass<*>): BaseSelector? {
    val sel = allInnerSelectorBlocks + allInnerSelectors
    return sel.find { it.hasAnnotation(ann) }
}

fun <T: Block> T.findAllWithAnnotation(ann: KClass<*>): Collection<BaseSelector> {
    val sel = allInnerSelectorBlocks + allInnerSelectors
    return sel.filter { it.hasAnnotation(ann) }
}