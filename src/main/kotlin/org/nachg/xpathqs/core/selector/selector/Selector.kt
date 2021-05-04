package org.nachg.xpathqs.core.selector.selector

import org.nachg.xpathqs.core.selector.NullSelector
import org.nachg.xpathqs.core.selector.base.BaseSelector
import org.nachg.xpathqs.core.selector.base.ISelector
import org.nachg.xpathqs.core.selector.base.SelectorState

open class Selector(
    state: SelectorState = SelectorState.INIT,
    base: ISelector = NullSelector(),
    name: String = "",

    override val props: SelectorProps = SelectorProps()
) : BaseSelector(
    state = state,
    base = base,
    name = name,
    props = props
)