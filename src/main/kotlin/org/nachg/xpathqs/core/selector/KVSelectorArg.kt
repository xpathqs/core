package org.nachg.xpathqs.core.selector

open class KVSelectorArg(
    protected val k: String,
    protected val v: String,
    joinType: JoinType = JoinType.NONE
) : SelectorArg(
    value = "$k=$v",
    joinType
) {
    override val key: String
        get() = k
}