package org.nachg.xpathqs.core.selector

open class SelectorProps(
    val prefix: String = "//",
    val tag: String = "*",
    val props: SelectorArgs = SelectorArgs()
): BaseSelectorProps(props) {
    override fun toXpath(): String {
        return "$prefix$tag${args.toXpath()}"
    }

    fun clone(
        prefix: String = this.prefix,
        tag: String = this.tag,
        props: SelectorArgs = this.props
    ): SelectorProps {
        return SelectorProps(prefix, tag, props.clone())
    }

    override fun clone(): SelectorProps {
        return SelectorProps(prefix, tag, props.clone())
    }
}
