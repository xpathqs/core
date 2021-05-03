package org.nachg.xpathqs.core.selector

open class SelectorArg(
    val value: String,
    val joinTypeType: JoinType = JoinType.NONE
) {
    fun toXpath(): String {
        return when(joinTypeType) {
            JoinType.NONE -> value
            JoinType.OR -> " or $value "
            JoinType.AND -> " and $value "
        }
    }

    open val key: String
        get() = value
}