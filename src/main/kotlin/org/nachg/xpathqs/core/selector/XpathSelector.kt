package org.nachg.xpathqs.core.selector

class XpathSelector(
    val xpath: String = "",

    state: SelectorState = SelectorState.INIT,
    base: ISelector = NullSelector(),
    name: String = "",

    props: BaseSelectorProps = BaseSelectorProps()

) : BaseSelector(state, base, name, props) {
    override fun toXpath(): String {
        return base.toXpath() + xpath + props.toXpath()
    }
}