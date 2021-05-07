package org.xpathqs.core.selector.base

import org.xpathqs.core.selector.args.SelectorArgs

open class BaseSelectorProps(
    val args: SelectorArgs = SelectorArgs()
) : Cloneable {

    open fun toXpath(): String {
        return args.toXpath()
    }

    public override fun clone(): BaseSelectorProps {
        return BaseSelectorProps(args.clone())
    }
}