package org.nachg.xpathqs.core.reflection

import org.nachg.xpathqs.core.selector.Block
import org.nachg.xpathqs.core.selector.NullSelector
import org.nachg.xpathqs.core.selector.base.ISelector
import org.nachg.xpathqs.core.selector.selector.Selector

internal class SelectorParser(
    private val rootObj: Block,
    private val base: ISelector = NullSelector(),
    private val srf: SelectorReflectionFields = SelectorReflectionFields(rootObj)
) {
    fun parse() {
        val baseName = if (base.name.isNotEmpty()) base.name + "." else ""

        rootObj.setBase(base)
        rootObj.setName(baseName + rootObj::class.simpleName!!)
        rootObj.freeze()

        srf.innerSelectorFields.forEach {
            it.isAccessible = true
            val sel = it.get(rootObj) as Selector
            sel.setName(rootObj.name + "." + it.name)
            sel.setBase(rootObj)
            sel.freeze()
        }
        rootObj.children = srf.innerSelectors

        srf.innerBlocks.forEach {
            SelectorParser(it, rootObj).parse()
        }
    }
}