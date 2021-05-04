package org.nachg.xpathqs.core.selector

import org.nachg.xpathqs.core.reflection.setProps

fun <T : Selector> T.tag(value: String): T {
    val res = this.clone()
    res.setProps(props.clone(tag = value))
    return res
}

operator fun <T : BaseSelector> T.get(value: String) = get(SelectorArg(value))

operator fun <T : BaseSelector> T.get(pos: Int) = get(KVSelectorArg("position()", pos.toString()))

operator fun <T : BaseSelector> T.get(arg: SelectorArg): T {
    val res = this.clone()
    res.props.args.add(
        arg
    )
    return res
}