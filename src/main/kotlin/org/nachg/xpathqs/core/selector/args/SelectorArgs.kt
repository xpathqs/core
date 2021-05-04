package org.nachg.xpathqs.core.selector.args

class SelectorArgs(
    private val args: HashMap<String, SelectorArg> = HashMap()
) : Cloneable {
    fun toXpath(): String {
        if (args.isEmpty()) return ""

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
        if (args.isNotEmpty() && arg.isNone) {
            arg.joinType = JoinType.AND
        }
        args[arg.key] = arg
        return this
    }

    @Suppress("UNCHECKED_CAST")
    public override fun clone(): SelectorArgs {
        return SelectorArgs(args.clone() as HashMap<String, SelectorArg>)
    }
}