package org.nac.xpathqs.core.selector

import org.nac.xpathqs.core.reflection.setBase

open class Block(
    internal val isBlank: Boolean = true,
    base: ISelector = NullSelector(),
    props: SelectorProps = SelectorProps(),
): Selector(
    base = base,
    props = props
) {
    internal var originBlock: Block = this
    internal var children: Collection<Selector> = emptyList()

    constructor(sel: Selector): this(
        isBlank = false,
        base = sel.base.clone(),
        props = sel.props.copy()
    )

    override fun toXpath(): String {
        if(isBlank) {
            return ""
        }

        val res = super.toXpath()

        fixChildrenSelectors()

        return res
    }

    protected fun fixChildrenSelectors() {
        if(state == SelectorState.CLONED) {
            children.forEach {
                it.setBase(originBlock)
            }
        }
    }
}