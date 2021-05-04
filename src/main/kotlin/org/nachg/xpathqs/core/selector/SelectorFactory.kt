package org.nachg.xpathqs.core.selector

object SelectorFactory {
    fun tagSelector(tag: String) = Selector(props = SelectorProps(tag = tag))
}