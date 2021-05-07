package org.xpathqs.core.selector.base

import org.xpathqs.core.selector.NullSelector

abstract class BaseSelector(
    internal val state: SelectorState = SelectorState.INIT,
    internal val base: ISelector = NullSelector(),
    override val name: String = "",

    internal open val props: BaseSelectorProps = BaseSelectorProps()
) : ISelector {

    override fun toXpath(): String {
        return base.toXpath() + props.toXpath()
    }

    override fun toString(): String {
        return name
    }
}