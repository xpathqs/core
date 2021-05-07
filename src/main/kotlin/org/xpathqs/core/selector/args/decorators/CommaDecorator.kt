package org.xpathqs.core.selector.args.decorators

import org.xpathqs.core.selector.args.KVSelectorArg

class CommaDecorator(private val wrapper: KVSelectorArg) : KVSelectorArg(wrapper.k, wrapper.v) {
    override val v: String
        get() = escape("'${wrapper.v}'")

    companion object {
        fun escape(str: String): String {
            val value = str.removePrefix("'").removeSuffix("'")
            if (value.contains("'")) {
                return "concat('${value.replace("'", "',\"'\",'")}')"
            }
            return str
        }
    }
}