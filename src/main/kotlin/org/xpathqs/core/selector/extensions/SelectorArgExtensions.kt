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

import org.xpathqs.core.constants.Global
import org.xpathqs.core.reflection.SelectorReflection
import org.xpathqs.core.selector.args.KVSelectorArg
import org.xpathqs.core.selector.args.SelectorArgs
import org.xpathqs.core.selector.args.ValueArg
import org.xpathqs.core.selector.args.decorators.CommaDecorator
import org.xpathqs.core.selector.args.decorators.ContainsDecorator
import org.xpathqs.core.selector.args.decorators.KVNormalizeSpaceDecorator
import org.xpathqs.core.selector.base.BaseSelector
import org.xpathqs.core.selector.extensions.core.clone

/**
 * Add `text` argument query
 */
fun <T : BaseSelector> T.text(
    text: String,
    contains: Boolean = false,
    normalize: Boolean = false
) = arg(Global.TEXT_ARG, text, contains, normalize)

/**
 * Add `string-length([Global.TEXT_ARG]) > 0` argument
 */
fun <T : BaseSelector> T.textNotEmpty()
        = addStringToArgs("string-length(${Global.TEXT_ARG}) > 0")

/**
 * Add `string-length(normalize-space([Global.TEXT_ARG])) > 0` argument
 */
fun <T : BaseSelector> T.normalizedTextNotEmpty()
        = addStringToArgs("string-length(normalize-space(${Global.TEXT_ARG})) > 0")
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
) = selfClone {
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
}

/**
 * Add string-value to the argument query
 */
fun <T : BaseSelector> T.addStringToArgs(
    value: String
) = selfClone {
    props.args.add(
        ValueArg(value)
    )
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
 * Add provided attribute value
 *
 * Require #1 - [value] argument should be added as an argument value
 *      for all(*) properties
 * @sample org.xpathqs.core.selector.extensions.SelectorArgTests.r1_withAttributeValue
 */
fun <T : BaseSelector> T.withAttributeValue(value: String)
    = arg("@*", value)

/**
 * Add provided attribute
 *
 * Require #1 - [attribute] name should be added as a parameter without value
 * @sample org.xpathqs.core.selector.extensions.SelectorArgTests.r1_withAttribute
 */
fun <T : BaseSelector> T.withAttribute(attribute: String) = selfClone {
    props.args.add(
        "@$attribute"
    )
}

/**
 * Add count of any children to the xpath
 *
 * Require #1 - [count] elements of [tag] should be added into xpath
 * @sample org.xpathqs.core.selector.extensions.SelectorArgTests.r1_childCount
 */
fun <T : BaseSelector> T.childCount(count: Int, tag: String = "*")
    = selfClone {
        props.args.add(
            KVSelectorArg("count($tag)", count.toString())
        )
    }
