package org.xpathqs.core.selector

import org.xpathqs.core.reflection.freeze
import org.xpathqs.core.reflection.setBase
import org.xpathqs.core.selector.base.BaseSelector
import org.xpathqs.core.selector.base.ISelector
import org.xpathqs.core.selector.base.SelectorState
import org.xpathqs.core.selector.extensions.clone
import org.xpathqs.core.selector.group.GroupSelector
import org.xpathqs.core.selector.selector.Selector
import org.xpathqs.core.selector.selector.SelectorProps

open class Block(
    base: ISelector = NullSelector(),
    props: SelectorProps = SelectorProps(),
    selectorsChain: ArrayList<BaseSelector> = ArrayList(),
    name: String = "",
    internal val isBlank: Boolean = true,
) : GroupSelector(
    base = base,
    props = props,
    name = name,
    selectorsChain = selectorsChain
) {
    internal var originBlock: ISelector = NullSelector()
    internal var children: Collection<Selector> = emptyList()

    constructor(sel: Selector) : this(
        isBlank = false,
        base = sel.base.clone(),
        props = sel.props.clone(),
        selectorsChain = arrayListOf(sel.clone())
    )

    constructor(sel: XpathSelector) : this(
        isBlank = false,
        base = sel.base.clone(),
        props = SelectorProps(
            prefix = "",
            tag = sel.tag,
            args = sel.props.args
        ),
        selectorsChain = arrayListOf(sel.clone())
    )

    constructor(sel: GroupSelector) : this(
        isBlank = false,
        base = sel.base.clone(),
        props = SelectorProps(
            prefix = "",
            tag = sel.tag,
            args = sel.props.args
        ),
        selectorsChain = sel.selectorsChain
    )

    override fun toXpath(): String {
        if (isBlank) {
            return ""
        }

        val res = super.toXpath()

        fixChildrenSelectors()

        return res
    }

    protected fun fixChildrenSelectors() {
        if (state == SelectorState.CLONED) {
            children.forEach {
                it.setBase(originBlock)
            }
            freeze()
        }
    }
}