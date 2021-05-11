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

package org.xpathqs.core.selector.extensions

import org.xpathqs.core.constants.Global
import org.xpathqs.core.reflection.SelectorReflection
import org.xpathqs.core.reflection.setProps
import org.xpathqs.core.selector.XpathSelector
import org.xpathqs.core.selector.args.KVSelectorArg
import org.xpathqs.core.selector.args.SelectorArgs
import org.xpathqs.core.selector.args.ValueArg
import org.xpathqs.core.selector.args.decorators.CommaDecorator
import org.xpathqs.core.selector.args.decorators.ContainsDecorator
import org.xpathqs.core.selector.args.decorators.KVNormalizeSpaceDecorator
import org.xpathqs.core.selector.base.BaseSelector
import org.xpathqs.core.selector.base.ISelector
import org.xpathqs.core.selector.compose.ComposeSelector
import org.xpathqs.core.selector.group.GroupSelector
import org.xpathqs.core.selector.selector.Selector
import org.xpathqs.core.util.SelectorFactory.compose
import org.xpathqs.core.util.SelectorFactory.xpathSelector

/**
 * Modifies `tag` value of [Selector]
 */
fun <T : Selector> T.tag(value: String): T {
    val res = this.clone()
    res.setProps(props.clone(tag = value))
    return res
}

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
 * Modifies `prefix` value of [Selector]
 */
fun <T : Selector> T.prefix(value: String): T {
    val res = this.clone()
    res.setProps(props.clone(prefix = value))
    return res
}

/**
 * Remove all arguments from the Selector
 */
fun <T : BaseSelector> T.removeParams(): T {
    val res = this.clone()
    SelectorReflection(res).setArgs(SelectorArgs())
    return res
}

/**
 * Add new argument to the selector
 */
operator fun <T : BaseSelector> T.get(value: String) = get(ValueArg(value))

/**
 * Add position argument to the selector
 */
operator fun <T : BaseSelector> T.get(pos: Int) = get(KVSelectorArg("position()", pos.toString()))

/**
 * Add new argument to the selector
 */
operator fun <T : BaseSelector> T.get(arg: ValueArg): T {
    val res = this.clone()
    res.props.args.add(
        arg
    )
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

/**
 * Returns [GroupSelector] with based on `left` and `right` arguments
 */
operator fun <T : BaseSelector> T.plus(sel: BaseSelector): GroupSelector {
    return GroupSelector(selectorsChain = arrayListOf(this.clone(), sel.clone()))
}

/**
 * Returns [GroupSelector] with based on `left` and `right` arguments
 */
operator fun <T : BaseSelector> T.plus(xpath: String) = this.plus(xpathSelector(xpath))

/**
 * Add `text` argument query
 */
fun <T : BaseSelector> T.text(
    text: String,
    contains: Boolean = false,
    normalize: Boolean = false
) = arg(Global.TEXT_ARG, text, contains, normalize)

/**
 * Add `id` argument query
 */
fun <T : BaseSelector> T.id(
    text: String,
    contains: Boolean = false,
    normalize: Boolean = false
) = arg(Global.ID_ARG, text, contains, normalize)

/**
 * Add argument query
 */
fun <T : BaseSelector> T.arg(
    argName: String,
    value: String,
    contains: Boolean = false,
    normalize: Boolean = false
): T {
    val res = this.clone()

    var arg: ValueArg =
        CommaDecorator(KVSelectorArg(argName, value))

    if (normalize) {
        arg = KVNormalizeSpaceDecorator(arg as KVSelectorArg)
    }

    if (contains) {
        arg = ContainsDecorator(arg as KVSelectorArg)
    }

    res.props.args.add(
        arg
    )

    return res
}

/**
 * Returns a [ComposeSelector] based on `left` and `right` arguments
 * @sample org.xpathqs.core.selector.compose.ComposeSelectorTests.divOperator
 * @sample org.xpathqs.core.selector.compose.ComposeSelectorTests.divOperatorPriority
 */
operator fun <T : ISelector> T.div(right: ISelector): ComposeSelector {
    return compose(this.clone(), right.clone())
}

/**
 * Returns repeated [count] times xpath as [XpathSelector]
 * @sample org.xpathqs.core.selector.extensions.SelectorModificationTests.repeatTest
 */
fun <T : BaseSelector> T.repeat(count: Int): XpathSelector {
    val xpath = this.toXpath()

    var res = ""
    repeat(count) {
        res += xpath
    }

    return xpathSelector(res)
}

