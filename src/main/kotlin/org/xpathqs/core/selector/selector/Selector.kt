package org.xpathqs.core.selector.selector

import org.xpathqs.core.selector.NullSelector
import org.xpathqs.core.selector.base.BaseSelector
import org.xpathqs.core.selector.base.ISelector
import org.xpathqs.core.selector.base.SelectorState

open class Selector(
    state: SelectorState = SelectorState.INIT,
    base: ISelector = NullSelector(),
    name: String = "",
    override val props: SelectorProps = SelectorProps()
) : BaseSelector(
    state = state,
    base = base,
    name = name,
    props = props,
) {
    override val tag: String
        get() = props.tag

    val prefix: String
        get() = props.prefix
}