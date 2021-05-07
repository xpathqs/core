package org.xpathqs.core.selector.extensions

import org.xpathqs.core.reflection.setProps
import org.xpathqs.core.constants.Global
import org.xpathqs.core.selector.args.KVSelectorArg
import org.xpathqs.core.selector.args.ValueArg
import org.xpathqs.core.selector.args.decorators.CommaDecorator
import org.xpathqs.core.selector.args.decorators.ContainsDecorator
import org.xpathqs.core.selector.args.decorators.KVNormalizeSpaceDecorator
import org.xpathqs.core.selector.base.BaseSelector
import org.xpathqs.core.selector.base.ISelector
import org.xpathqs.core.selector.compose.ComposeSelector
import org.xpathqs.core.selector.selector.Selector
import org.xpathqs.core.util.SelectorFactory.compose

fun <T : Selector> T.tag(value: String): T {
    val res = this.clone()
    res.setProps(props.clone(tag = value))
    return res
}

operator fun <T : BaseSelector> T.get(value: String) = get(ValueArg(value))

operator fun <T : BaseSelector> T.get(pos: Int) = get(KVSelectorArg("position()", pos.toString()))

operator fun <T : BaseSelector> T.get(arg: ValueArg): T {
    val res = this.clone()
    res.props.args.add(
        arg
    )
    return res
}

fun <T : BaseSelector> T.text(
    text: String,
    contains: Boolean = false,
    normalize: Boolean = false
) = arg(Global.TEXT_ARG, text, contains, normalize)

fun <T : BaseSelector> T.arg(
    argName: String,
    value: String,
    contains: Boolean = false,
    normalize: Boolean = false
): T {
    val res = this.clone()

    var arg: ValueArg =
        CommaDecorator(KVSelectorArg(argName, value))

    if (normalize) {
        arg = KVNormalizeSpaceDecorator(arg as KVSelectorArg)
    }

    if (contains) {
        arg = ContainsDecorator(arg as KVSelectorArg)
    }

    res.props.args.add(
        arg
    )

    return res
}

operator fun <T : ISelector> T.div(right: ISelector): ComposeSelector {
    return compose(this.clone(), right.clone())
}