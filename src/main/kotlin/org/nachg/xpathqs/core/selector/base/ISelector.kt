package org.nachg.xpathqs.core.selector.base

interface ISelector : Cloneable {
    fun toXpath(): String
    val name: String
        get() = ""
}