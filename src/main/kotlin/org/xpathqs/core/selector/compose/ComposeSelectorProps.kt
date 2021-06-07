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

package org.xpathqs.core.selector.compose

import org.xpathqs.core.selector.args.SelectorArgs
import org.xpathqs.core.selector.base.BaseSelectorProps
import org.xpathqs.core.selector.base.ISelector
import org.xpathqs.core.selector.extensions.core.clone

/**
 * [ComposeSelector] properties holder.
 * @param selectors collection of combined selectors
 * @sample org.xpathqs.core.selector.compose.ComposeSelectorPropsTests
 */
class ComposeSelectorProps(
    internal val selectors: ArrayList<ISelector> = ArrayList(),
    args: SelectorArgs = SelectorArgs()
) : BaseSelectorProps(args = args) {

    /**
     * Add new selector to the [selectors] collection
     */
    fun add(sel: ISelector) {
        selectors.add(sel)
    }

    /**
     * Get xpath query based on [selectors]
     */
    override fun toXpath(): String {
        if (selectors.isEmpty()) {
            return ""
        }
        if (selectors.size == 1) {
            return selectors.first().toXpath()
        }
        var res = ""
        selectors.forEach {
            val xp = it.toXpath()
            if (xp.isNotEmpty()) {
                res += "($xp) | "
            }
        }

        res = res.removeSuffix(" | ")
        val args = args.toXpath()

        if (args.isNotEmpty()) {
            return "($res)$args"
        }

        return res
    }

    override fun clone(): ComposeSelectorProps {
        val clonedSelectors = ArrayList<ISelector>()
        selectors.forEach {
            clonedSelectors.add(it.clone())
        }

        return ComposeSelectorProps(
            clonedSelectors,
            args.clone()
        )
    }
}