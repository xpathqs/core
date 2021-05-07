package org.xpathqs.core.selector.args

open class ValueArg(
    internal open val value: String = "",
    internal var joinType: JoinType = JoinType.NONE
) {
    fun toXpath(): String {
        return when (joinType) {
            JoinType.NONE -> value
            JoinType.OR -> " or $value "
            JoinType.AND -> " and $value "
        }
    }

    open val key: String
        get() = value

    val isNone: Boolean
        get() = joinType == JoinType.NONE
}