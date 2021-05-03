package org.nac.xpathqs.core.selector

interface ISelector: Cloneable {
    fun toXpath(): String
}