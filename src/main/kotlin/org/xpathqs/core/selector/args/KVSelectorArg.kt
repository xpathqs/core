package org.xpathqs.core.selector.args

open class KVSelectorArg(
    open val k: String,
    open val v: String,
    joinType: JoinType = JoinType.NONE
) : ValueArg(
    joinType = joinType
) {
    override val value: String
        get() = "$k=$v"

    override val key: String
        get() = k
}
