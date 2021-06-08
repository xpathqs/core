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

package org.xpathqs.core.selector.group

import org.xpathqs.core.selector.args.ValueArg
import org.xpathqs.core.selector.base.BaseSelector
import org.xpathqs.core.selector.extensions.addArg
import org.xpathqs.core.selector.extensions.core.clone
import org.xpathqs.core.selector.extensions.prefix
import org.xpathqs.core.selector.extensions.tag
import org.xpathqs.core.selector.selector.Selector

/**
 * Modifies `tag` value of [GroupSelector]
 */
fun <T : GroupSelector> T.tag(value: String): T {
    if (this.selectorsChain.size == 1) {
        val first = this.selectorsChain.first()
        if (first is Selector) {
            val res = this.clone()
            res.selectorsChain = arrayListOf(first.tag(value))
            return res
        }
    }
    return this
}

/**
 * Modifies `prefix` value of [GroupSelector]
 */
fun <T : GroupSelector> T.prefix(value: String): T {
    val first = this.selectorsChain.first()
    if (first is Selector) {
        val res = this.clone()
        val chain: ArrayList<BaseSelector> = arrayListOf(first.prefix(value))
        this.selectorsChain.removeFirst()
        chain.addAll(this.selectorsChain)
        res.selectorsChain = chain
        return res
    }

    return this
}

/**
 * Modifies `tag` value of [GroupSelector]
 */
fun <T : GroupSelector> T.addGroupArg(arg: ValueArg): T {
    val res = this.clone()
    if (this.selectorsChain.size == 1) {
        val first = this.selectorsChain.first()
        res.selectorsChain = arrayListOf(first.addArg(arg))
    } else {
        res.props.args.add(
            arg
        )
    }
    return res
}

/**
 * Add new selector to the [GroupSelector.selectorsChain]
 */
operator fun <T : GroupSelector> T.plus(sel: BaseSelector): T {
    val res = this.clone()
    res.add(
        sel.clone()
    )
    return res
}