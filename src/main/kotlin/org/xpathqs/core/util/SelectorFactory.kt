package org.xpathqs.core.util

import org.xpathqs.core.constants.Global
import org.xpathqs.core.selector.args.KVSelectorArg
import org.xpathqs.core.selector.args.SelectorArgs
import org.xpathqs.core.selector.args.decorators.ContainsDecorator
import org.xpathqs.core.selector.base.ISelector
import org.xpathqs.core.selector.compose.ComposeSelector
import org.xpathqs.core.selector.compose.ComposeSelectorProps
import org.xpathqs.core.selector.selector.Selector
import org.xpathqs.core.selector.selector.SelectorProps

object SelectorFactory {
    fun tagSelector(tag: String = "*") = Selector(props = SelectorProps(tag = tag))

    fun textSelector(text: String) = Selector(
        props = SelectorProps(
            args = SelectorArgs(
                KVSelectorArg(
                    k = Global.TEXT_ARG,
                    v = text
                )
            )
        )
    )

    fun textContainsSelector(text: String) = Selector(
        props = SelectorProps(
            args = SelectorArgs(
                ContainsDecorator(
                    KVSelectorArg(
                        k = Global.TEXT_ARG,
                        v = text
                    )
                )
            )
        )
    )

    fun idSelector(id: String) = Selector(
        props = SelectorProps(
            args = SelectorArgs(
                KVSelectorArg(
                    k = Global.ID_ARG,
                    v = id
                )
            )
        )
    )

    fun idContainsSelector(id: String) = Selector(
        props = SelectorProps(
            args = SelectorArgs(
                ContainsDecorator(
                    KVSelectorArg(
                        k = Global.ID_ARG,
                        v = id
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