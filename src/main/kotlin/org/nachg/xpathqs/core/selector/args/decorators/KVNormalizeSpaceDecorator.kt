package org.nachg.xpathqs.core.selector.args.decorators

import org.nachg.xpathqs.core.selector.args.KVSelectorArg

class KVNormalizeSpaceDecorator(private val wrapper: KVSelectorArg) : KVSelectorArg(wrapper.k, wrapper.v) {
    override val v: String
        get() = "normalize-space(${wrapper.v})"
}