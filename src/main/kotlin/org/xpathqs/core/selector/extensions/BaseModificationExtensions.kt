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

package org.xpathqs.core.selector.extensions

import org.xpathqs.core.selector.args.ValueArg
import org.xpathqs.core.selector.base.BaseSelector
import org.xpathqs.core.selector.base.ISelector
import org.xpathqs.core.selector.compose.ComposeSelector
import org.xpathqs.core.selector.extensions.core.clone
import org.xpathqs.core.selector.group.GroupSelector
import org.xpathqs.core.selector.selector.Selector
import org.xpathqs.core.selector.xpath.XpathSelector
import org.xpathqs.core.util.SelectorFactory.compose
import org.xpathqs.core.util.SelectorFactory.xpathSelector


/**
 * Add a selector as an parameter's argument
 *
 * Require #1 - [right] selector's xpath should be added to the target selector as an parameter
 * @sample org.xpathqs.core.selector.base.extension.SelectorParametersTest.r1_contains
 */
infix fun <T : BaseSelector> T.contains(right: Selector) = selfClone {
    props.args.add(
        right.prefix("./").toXpath()
    )
}

/**
 * Add a selector as an parameter's argument
 *
 * Require #2 - when [right] selector's xpath starts with dot then
 *      it should be added to the target selector parameters as it is
 * @sample org.xpathqs.core.selector.base.extension.SelectorParametersTest.r2_containsXpathWithPrefix
 *
 * Require #3 - when [right] selector's xpath doesn't starts with dot then
 *      it should be added to the target selector parameters with dot prefix
 * @sample org.xpathqs.core.selector.base.extension.SelectorParametersTest.r3_containsXpathWithoutPrefix
 */
infix fun <T : BaseSelector> T.contains(right: XpathSelector) = selfClone {
    var xpath = right.toXpath()
    if (!xpath.startsWith(".")) {
        xpath = "./$xpath"
    }
    props.args.add(xpath)
}

/**
 * Add a selector as an parameter's argument as a parent context
 *
 * Require #1 - [right] selector's xpath should be added to the target selector as an parameter
 * @sample org.xpathqs.core.selector.base.extension.SelectorParametersTest.r1_containsParent
 */
infix fun <T : BaseSelector> T.containsParent(right: Selector) = selfClone {
    props.args.add(
        ValueArg(
            right.prefix("../").toXpath()
        )
    )
}

/**
 * Add a selector as an parameter's argument as a parent context
 *
 * Require #2 - when [right] selector's xpath starts with dot then
 *      it should be added to the target selector parameters as it is
 * @sample org.xpathqs.core.selector.base.extension.SelectorParametersTest.r2_containsParentXpathWithPrefix
 *
 * Require #3 - when [right] selector's xpath doesn't starts with dot then
 *      it should be added to the target selector parameters with dot prefix
 * @sample org.xpathqs.core.selector.base.extension.SelectorParametersTest.r3_containsParentXpathWithoutPrefix
 */
infix fun <T : BaseSelector> T.containsParent(right: XpathSelector) = selfClone {
    var xpath = right.toXpath()
    if (!xpath.startsWith(".")) {
        xpath = "../$xpath"
    }
    props.args.add(xpath)
}

/**
 * Add new argument to the selector
 */
fun <T : BaseSelector> T.addArg(arg: ValueArg) = selfClone {
    props.args.add(
        arg
    )
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

fun <T : BaseSelector> T.selfClone(f: BaseSelector.() -> Unit): T {
    val res = this.clone()
    res.f()
    return res
}

