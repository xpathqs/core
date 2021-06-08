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

import org.xpathqs.core.selector.NullSelector
import org.xpathqs.core.selector.base.BaseSelector
import org.xpathqs.core.selector.base.BaseSelectorProps
import org.xpathqs.core.selector.base.ISelector
import org.xpathqs.core.selector.base.SelectorState

/**
 * Class with ability to combine several selectors
 * @param selectorsChain - list of selector used to build xpath
 * @sample org.xpathqs.core.selector.group.GroupSelectorTest
 */
open class GroupSelector(
    state: SelectorState = SelectorState.INIT,
    base: ISelector = NullSelector(),
    name: String = "",
    props: BaseSelectorProps = BaseSelectorProps(),

    internal var selectorsChain: ArrayList<BaseSelector> = ArrayList(),
) : BaseSelector(
    state = state, base = base, name = name, props = props
) {
    /**
     * Add new selector to the [selectorsChain]
     */
    internal fun add(sel: BaseSelector): GroupSelector {
        selectorsChain.add(sel)
        return this
    }

    /**
     * tag doesn't supported for the [GroupSelector]
     */
    override val tag: String
        get() = ""

    /**
     * Return xpath of [base] selector + [selectorsChain] combined result
     */
    override fun toXpath() =
        base.toXpath() + selfXpath()

    private fun selfXpath(): String {
        if (selectorsChain.isEmpty()) {
            return ""
        }
        if (selectorsChain.size == 1) {
            return selectorsChain.first().toXpath()
        }
        var res = ""
        selectorsChain.forEach {
            res += it.toXpath()
        }
        val props = props.toXpath()
        if (props.isNotEmpty()) {
            return "($res)$props"
        }
        return res
    }
}