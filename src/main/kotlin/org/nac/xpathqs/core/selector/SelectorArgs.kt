package org.nac.xpathqs.core.selector

import java.lang.StringBuilder

class SelectorArgs(
    private val args: HashMap<String, SelectorArg> = HashMap()
): Cloneable {
    fun toXpath(): String {
        if(args.isEmpty()) return ""

        val sb = StringBuilder()
        args.values.forEach {
            sb.append(it.toXpath())
        }

        val res = sb
            .toString()
            .trim()
            .replace("  ", " ")

        return "[$res]"
    }

    fun add(arg: SelectorArg): SelectorArgs {
        args[arg.key] = arg
        return this
    }

    fun drop(arg: SelectorArg): SelectorArgs {
        args.remove(arg.key)
        return this
    }

    @Suppress("UNCHECKED_CAST")
    override fun clone(): SelectorArgs {
        return SelectorArgs(args.clone() as HashMap<String, SelectorArg>)
    }
}