package org.xpathqs.core.util

import org.xpathqs.core.constants.Global
import org.xpathqs.core.selector.XpathSelector
import org.xpathqs.core.selector.args.KVSelectorArg
import org.xpathqs.core.selector.args.SelectorArgs
import org.xpathqs.core.selector.args.decorators.CommaDecorator
import org.xpathqs.core.selector.args.decorators.ContainsDecorator
import org.xpathqs.core.selector.base.ISelector
import org.xpathqs.core.selector.compose.ComposeSelector
import org.xpathqs.core.selector.compose.ComposeSelectorProps
import org.xpathqs.core.selector.selector.Selector
import org.xpathqs.core.selector.selector.SelectorProps

object SelectorFactory {
    fun tagSelector(tag: String = "*") = Selector(props = SelectorProps(tag = tag))

    fun xpathSelector(xpath: String) = XpathSelector(xpath)

    fun textSelector(text: String) = Selector(
        props = SelectorProps(
            args = SelectorArgs(
                CommaDecorator(
                    KVSelectorArg(
                        k = Global.TEXT_ARG,
                        v = text
                    )
                )
            )
        )
    )

    fun textContainsSelector(text: String) = Selector(
        props = SelectorProps(
            args = SelectorArgs(
                ContainsDecorator(
                    CommaDecorator(
                        KVSelectorArg(
                            k = Global.TEXT_ARG,
                            v = text
                        )
                    )
                )
            )
        )
    )

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

    fun compose(vararg selectors: ISelector) =
        ComposeSelector(
            props = ComposeSelectorProps(
                selectors = ArrayList<ISelector>().apply {
                    addAll(selectors)
                }
            )
        )
}