package org.nac.xpathqs.core.reflection

import org.nac.xpathqs.core.selector.Block
import org.nac.xpathqs.core.selector.ISelector
import org.nac.xpathqs.core.selector.NullSelector
import org.nac.xpathqs.core.selector.Selector

class SelectorParser(
    private val rootObj: Selector,
    private val base: ISelector = NullSelector(),
    private val srf: SelectorReflectionFields = SelectorReflectionFields(rootObj)
) {
    fun parse() {
        if(base is NullSelector) {
            rootObj.setName(rootObj::class.simpleName!!)
            rootObj.freeze()

            if(rootObj is Block) {
                srf.innerSelectorFields.forEach {
                    it.isAccessible = true
                    val sel = it.get(rootObj) as Selector
                    sel.setName(rootObj.name + "." + it.name)
                    sel.setBase(rootObj)
                    sel.freeze()
                }
                rootObj.children = srf.innerSelectors
            }
        }
    }

}