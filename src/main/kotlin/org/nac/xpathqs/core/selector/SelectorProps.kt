package org.nac.xpathqs.core.selector

data class SelectorProps(
    val prefix: String = "//",
    val tag: String = "*",
    val args: SelectorArgs = SelectorArgs()
) {
    fun toXpath(): String {
        return "$prefix$tag${args.toXpath()}"
    }
}
