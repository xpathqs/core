package org.nachg.xpathqs.core.selector

import org.nachg.xpathqs.core.reflection.setProps
import org.nachg.xpathqs.core.selector.args.KVSelectorArg
import org.nachg.xpathqs.core.selector.args.SelectorArg
import org.nachg.xpathqs.core.selector.base.BaseSelector
import org.nachg.xpathqs.core.selector.base.ISelector
import org.nachg.xpathqs.core.selector.compose.ComposeSelector
import org.nachg.xpathqs.core.selector.selector.Selector
import org.nachg.xpathqs.core.util.SelectorFactory.compose

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

operator fun <T : ISelector> T.div(right: ISelector): ComposeSelector {
    return compose(this.clone(), right.clone())
}