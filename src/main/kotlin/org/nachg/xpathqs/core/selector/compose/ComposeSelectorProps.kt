package org.nachg.xpathqs.core.selector.compose

import org.nachg.xpathqs.core.selector.args.SelectorArgs
import org.nachg.xpathqs.core.selector.base.BaseSelectorProps
import org.nachg.xpathqs.core.selector.base.ISelector
import org.nachg.xpathqs.core.selector.clone

class ComposeSelectorProps(
    internal val selectors: ArrayList<ISelector> = ArrayList(),
    args: SelectorArgs = SelectorArgs()
) : BaseSelectorProps(args = args) {

    fun add(sel: ISelector) {
        selectors.add(sel)
    }

    override fun toXpath(): String {
        if (selectors.isEmpty()) {
            return ""
        }
        if (selectors.size == 1) {
            return selectors.first().toXpath()
        }
        var res = ""
        selectors.forEach {
            val xp = it.toXpath()
            if (xp.isNotEmpty()) {
                res += "($xp) | "
            }
        }

        res = res.removeSuffix(" | ")
        val args = args.toXpath()

        if (args.isNotEmpty()) {
            return "($res)$args"
        }

        return res
    }

    override fun clone(): ComposeSelectorProps {
        val clonedSelectors = ArrayList<ISelector>()
        selectors.forEach {
            clonedSelectors.add(it.clone())
        }

        return ComposeSelectorProps(
            clonedSelectors,
            args.clone()
        )
    }
}