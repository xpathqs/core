package org.xpathqs.core.selector.selector

import org.xpathqs.core.selector.args.SelectorArgs
import org.xpathqs.core.selector.base.BaseSelectorProps

open class SelectorProps(
    val prefix: String = "//",
    val tag: String = "*",
    args: SelectorArgs = SelectorArgs()
) : BaseSelectorProps(args) {
    override fun toXpath(): String {
        return "$prefix$tag${args.toXpath()}"
    }

    fun clone(
        prefix: String = this.prefix,
        tag: String = this.tag,
        props: SelectorArgs = this.args
    ): SelectorProps {
        return SelectorProps(prefix, tag, props.clone())
    }

    override fun clone(): SelectorProps {
        return SelectorProps(prefix, tag, args.clone())
    }
}
