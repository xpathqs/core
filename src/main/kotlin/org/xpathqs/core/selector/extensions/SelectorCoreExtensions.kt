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

package org.xpathqs.core.selector.extensions.core

import org.xpathqs.core.selector.args.InnerSelectorArg
import org.xpathqs.core.selector.args.KVSelectorArg
import org.xpathqs.core.selector.args.SelectorArg
import org.xpathqs.core.selector.args.ValueArg
import org.xpathqs.core.selector.base.BaseSelector
import org.xpathqs.core.selector.base.ISelector
import org.xpathqs.core.selector.block.Block
import org.xpathqs.core.selector.extensions.addArg
import org.xpathqs.core.selector.group.GroupSelector
import org.xpathqs.core.selector.group.addGroupArg
import org.xpathqs.core.selector.selector.Selector
import org.xpathqs.core.selector.xpath.XpathSelector
import org.xpathqs.core.selector.base.deepClone as baseDeepClone
import org.xpathqs.core.selector.block.deepClone as blockDeepClone
import org.xpathqs.core.selector.group.deepClone as groupDeepClone
import org.xpathqs.core.selector.xpath.deepClone as xpathDeepClone

/**
 * Deep clone of the [ISelector] objects
 * Implementation depends on the selector's type
 */
@Suppress("UNCHECKED_CAST")
fun <T : ISelector> T.clone(): T {
    return when (this) {
        is Block -> blockDeepClone()
        is GroupSelector -> groupDeepClone()
        is Selector -> baseDeepClone()
        is XpathSelector -> xpathDeepClone() as T
        else -> this
    }
}

/**
 * Add position argument to the selector
 */
operator fun <T : BaseSelector> T.get(pos: Int) = get(KVSelectorArg("position()", pos.toString()))

/**
 * Add position argument to the selector
 */
operator fun <T : BaseSelector> T.get(arg: String) = get(ValueArg(arg))

/**
 * Add selector as an argument
 */
operator fun <T : BaseSelector> T.get(sel: BaseSelector)
    = get(SelectorArg(sel, type = InnerSelectorArg.ALL))

/**
 * Deep clone of the [ISelector] objects
 * Implementation depends on the selector's type
 */
@Suppress("UNCHECKED_CAST")
operator fun <T : BaseSelector> T.get(arg: ValueArg): T {
    return when (this) {
        is GroupSelector -> this.addGroupArg(arg)
        else -> this.addArg(arg)
    }
}

operator fun <T : BaseSelector> T.get(arg: Pair<String, String>)
    = get(KVSelectorArg(arg.first, arg.second))