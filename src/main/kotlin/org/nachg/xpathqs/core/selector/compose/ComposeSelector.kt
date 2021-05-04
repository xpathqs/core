package org.nachg.xpathqs.core.selector.compose

import org.nachg.xpathqs.core.selector.NullSelector
import org.nachg.xpathqs.core.selector.base.BaseSelector
import org.nachg.xpathqs.core.selector.base.ISelector
import org.nachg.xpathqs.core.selector.base.SelectorState

class ComposeSelector(
    state: SelectorState = SelectorState.INIT,
    base: ISelector = NullSelector(),
    name: String = "",

    override val props: ComposeSelectorProps = ComposeSelectorProps()
) : BaseSelector(
    state = state,
    base = base,
    name = name,
    props = props
) {
    override fun toXpath(): String {
        return props.toXpath()
    }
}