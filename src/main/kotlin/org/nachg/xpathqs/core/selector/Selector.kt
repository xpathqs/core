package org.nachg.xpathqs.core.selector

open class Selector(
    state: SelectorState = SelectorState.INIT,
    base: ISelector = NullSelector(),
    name: String = "",

    override val props: SelectorProps = SelectorProps()
): BaseSelector(
    state = state,
    base = base,
    name = name,
    props = props
)