package org.nachg.xpathqs.core.selector

interface ISelector : Cloneable {
    fun toXpath(): String
    val name: String
        get() = ""
}