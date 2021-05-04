package org.nachg.xpathqs.core.util

import org.nachg.xpathqs.core.selector.base.ISelector
import org.nachg.xpathqs.core.selector.compose.ComposeSelector
import org.nachg.xpathqs.core.selector.compose.ComposeSelectorProps
import org.nachg.xpathqs.core.selector.selector.Selector
import org.nachg.xpathqs.core.selector.selector.SelectorProps

object SelectorFactory {
    fun tagSelector(tag: String = "*") = Selector(props = SelectorProps(tag = tag))

    fun compose(vararg selectors: ISelector) =
        ComposeSelector(
            props = ComposeSelectorProps(
                selectors = ArrayList<ISelector>().apply {
                    addAll(selectors)
                }
            )
        )
}