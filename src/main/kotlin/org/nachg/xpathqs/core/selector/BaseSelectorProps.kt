package org.nachg.xpathqs.core.selector

open class BaseSelectorProps(
    val args: SelectorArgs = SelectorArgs()) : Cloneable {

    open fun toXpath(): String {
        return args.toXpath()
    }

    public override fun clone(): BaseSelectorProps {
        return BaseSelectorProps(args.clone())
    }
}