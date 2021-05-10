package org.xpathqs.core.selector.group

import org.xpathqs.core.selector.NullSelector
import org.xpathqs.core.selector.base.BaseSelector
import org.xpathqs.core.selector.base.BaseSelectorProps
import org.xpathqs.core.selector.base.ISelector
import org.xpathqs.core.selector.base.SelectorState

open class GroupSelector(
    state: SelectorState = SelectorState.INIT,
    base: ISelector = NullSelector(),
    name: String = "",
    props: BaseSelectorProps = BaseSelectorProps(),

    internal var selectorsChain: ArrayList<BaseSelector> = ArrayList(),
) : BaseSelector(
    state = state, base = base, name = name, props = props
) {
    internal fun add(sel: BaseSelector) {
        selectorsChain.add(sel)
    }

    override val tag: String
        get() = ""

    override fun toXpath() =
        base.toXpath() + selfXpath()

    private fun selfXpath(): String {
        if (selectorsChain.isEmpty()) {
            return ""
        }
        if (selectorsChain.size == 1) {
            return selectorsChain.first().toXpath()
        }
        var res = ""
        selectorsChain.forEach {
            res += it.toXpath()
        }
        val props = props.toXpath()
        if (props.isNotEmpty()) {
            return "($res)$props"
        }
        return  res
    }
}