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

package org.xpathqs.core.util

import org.xpathqs.core.constants.Global
import org.xpathqs.core.selector.args.KVSelectorArg
import org.xpathqs.core.selector.args.SelectorArgs
import org.xpathqs.core.selector.args.ValueArg
import org.xpathqs.core.selector.args.decorators.CommaDecorator
import org.xpathqs.core.selector.args.decorators.ContainsDecorator
import org.xpathqs.core.selector.args.decorators.KVNormalizeSpaceDecorator
import org.xpathqs.core.selector.base.ISelector
import org.xpathqs.core.selector.compose.ComposeSelector
import org.xpathqs.core.selector.compose.ComposeSelectorProps
import org.xpathqs.core.selector.extensions.addArg
import org.xpathqs.core.selector.extensions.addStringToArgs
import org.xpathqs.core.selector.selector.Selector
import org.xpathqs.core.selector.selector.SelectorProps
import org.xpathqs.core.selector.xpath.XpathSelector

/**
 * Builder factory methods
 */
object SelectorFactory {
    /**
     * Returns new [Selector] with specified `tag`
     */
    fun tagSelector(tag: String = "*") = Selector(props = SelectorProps(tag = tag))

    /**
     * Returns new [XpathSelector]
     */
    fun xpathSelector(xpath: String) = XpathSelector(xpath)

    /**
     * Returns new [Selector] with specified `text`
     */
    fun textSelector(text: String, normalize: Boolean = Global.NORMALIZE_TEXT_VALUE) = Selector(
        props = SelectorProps(
            args = SelectorArgs(
                KVNormalizeSpaceDecorator(
                    CommaDecorator(
                        KVSelectorArg(
                            k = Global.TEXT_ARG,
                            v = text
                        )
                    ),
                    normalizeK = normalize,
                    normalizeV = Global.NORMALIZE_TEXT_ARG,
                )
            )
        )
    )

    /**
     * Returns new [Selector] with containing specified `text`
     */
    fun textContainsSelector(text: String, normalize: Boolean = Global.NORMALIZE_TEXT_VALUE) = Selector(
        props = SelectorProps(
            args = SelectorArgs(
                ContainsDecorator(
                    KVNormalizeSpaceDecorator(
                        CommaDecorator(
                            KVSelectorArg(
                                k = Global.TEXT_ARG,
                                v = text
                            )
                        ),
                        normalizeK = normalize,
                        normalizeV = Global.NORMALIZE_TEXT_ARG,
                    )
                )
            )
        )
    )

    /**
     * Returns [Selector] with specified `id`
     */
    fun idSelector(id: String) = Selector(
        props = SelectorProps(
            args = SelectorArgs(
                CommaDecorator(
                    KVSelectorArg(
                        k = Global.ID_ARG,
                        v = id
                    )
                )
            )
        )
    )

    /**
     * Returns [Selector] with contains of specified `id`
     */
    fun idContainsSelector(id: String) = Selector(
        props = SelectorProps(
            args = SelectorArgs(
                ContainsDecorator(
                    CommaDecorator(
                        KVSelectorArg(
                            k = Global.ID_ARG,
                            v = id
                        )
                    )
                )
            )
        )
    )

    /**
     * Returns [Selector] based on attribute name and value
     *
     * Require #1 - default values should return [Selector] without limitation
     * @sample [org.xpathqs.core.util.SelectorFactoryAttrSelectorTest.withoutArgs]
     *
     * Require #2 - [name] argument should return [Selector] with attribute name
     * @sample [org.xpathqs.core.util.SelectorFactoryAttrSelectorTest.withName]
     *
     * Require #3 - [value] argument should return [Selector] with attribute value
     * @sample [org.xpathqs.core.util.SelectorFactoryAttrSelectorTest.withValue]
     *
     * Require #4 - [name] and [value] argument should return [Selector] with attributes name and value
     * @sample [org.xpathqs.core.util.SelectorFactoryAttrSelectorTest.withNameAndValue]
     *
     * Require #5 - [name] and [valueContains] argument should return [Selector] with attributes name
     *      and value wrapped into contains function
     * @sample [org.xpathqs.core.util.SelectorFactoryAttrSelectorTest.withNameAndValueContains]
     *
     * Require #6 - [valueContains] argument should return [Selector] with attribute value
     *    wrapped into contains function
     * @sample [org.xpathqs.core.util.SelectorFactoryAttrSelectorTest.withValueContains]
     */
    fun attrSelector(
        name: String = "*",
        value: String = "",
        valueContains: String = ""
    ): Selector {
        val checkedName = if(name.first() != '@') "@$name" else name
        val arg = if(value.isEmpty() && valueContains.isEmpty()) {
            KVSelectorArg(k = checkedName)
        } else {
            if(value.isNotEmpty()) {
                 CommaDecorator(
                    KVSelectorArg(
                        k = checkedName,
                        v = value
                    )
                )
            } else if(valueContains.isNotEmpty()) {
                 ContainsDecorator(
                    CommaDecorator(
                        KVSelectorArg(
                            k = checkedName,
                            v = valueContains
                        )
                    )
                )
            } else {
                ValueArg()
            }
        }

        return Selector(
            props = SelectorProps(
                args = SelectorArgs(
                    arg
                )
            )
        )
    }

    /**
     * Returns [ComposeSelector] based on provided [selectors]
     * @sample  org.xpathqs.core.util.PropertyFacadeTest
     */
    fun compose(vararg selectors: ISelector) =
        ComposeSelector(
            props = ComposeSelectorProps(
                selectors = ArrayList<ISelector>().apply {
                    addAll(selectors)
                }
            )
        )

    /**
     * Returns text selector were all [text] elements wrapped as a contains arg
     *
     * Require #1
     * when [text] has more than one arguments they all should be wrapped into contains function
     * @sample org.xpathqs.core.util.SelectorFactoryAttrSelectorTest.r1_textWithInnerTagsSelector
     *
     * Require #2
     * when [text] contains only one arg - result should same as a normal [textContainsSelector] call
     * @sample org.xpathqs.core.util.SelectorFactoryAttrSelectorTest.r2_textWithInnerTagsSelector
     */
    fun textWithInnerTagsSelector(vararg text: String): Selector {
        return if(text.size > 1) {
            val args = SelectorArgs(
                ContainsDecorator(
                    KVNormalizeSpaceDecorator(
                        CommaDecorator(
                            KVSelectorArg(
                                k = Global.TEXT_ARG,
                                v = text.first()
                            )
                        ),
                        normalizeK = Global.NORMALIZE_TEXT_VALUE,
                        normalizeV = Global.NORMALIZE_TEXT_ARG,
                    )
                )
            )
            text.drop(1).forEach {
                args.add(
                    ContainsDecorator(
                        CommaDecorator(
                            KVSelectorArg(
                                k = ".",
                                v = it
                            )
                        )
                    )
                )
            }
            Selector(
                props = SelectorProps(
                    args = args
                )
            )
        } else {
            textContainsSelector(text.first())
        }
    }
}