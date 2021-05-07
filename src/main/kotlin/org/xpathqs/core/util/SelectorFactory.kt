package org.xpathqs.core.util

import org.xpathqs.core.selector.base.ISelector
import org.xpathqs.core.selector.compose.ComposeSelector
import org.xpathqs.core.selector.compose.ComposeSelectorProps
import org.xpathqs.core.selector.selector.Selector
import org.xpathqs.core.selector.selector.SelectorProps

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