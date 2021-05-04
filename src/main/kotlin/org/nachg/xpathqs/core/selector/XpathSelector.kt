package org.nachg.xpathqs.core.selector

import org.nachg.xpathqs.core.selector.base.BaseSelector
import org.nachg.xpathqs.core.selector.base.BaseSelectorProps
import org.nachg.xpathqs.core.selector.base.ISelector
import org.nachg.xpathqs.core.selector.base.SelectorState

class XpathSelector(
    private val xpath: String = "",

    state: SelectorState = SelectorState.INIT,
    base: ISelector = NullSelector(),
    name: String = "",

    props: BaseSelectorProps = BaseSelectorProps()
) : BaseSelector(state, base, name, props) {
    override fun toXpath(): String {
        return base.toXpath() + xpath + props.toXpath()
    }
}