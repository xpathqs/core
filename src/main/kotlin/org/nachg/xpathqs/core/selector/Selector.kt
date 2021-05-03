package org.nachg.xpathqs.core.selector

open class Selector(
    internal val state: SelectorState = SelectorState.INIT,
    internal val base: ISelector = NullSelector(),
    internal val props: SelectorProps = SelectorProps(),

    internal val name: String = ""
): ISelector {
    override fun toXpath(): String {
        return "${base.toXpath()}${props.toXpath()}"
    }

    override fun toString(): String {
        return name
    }
}