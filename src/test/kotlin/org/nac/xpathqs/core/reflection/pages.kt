package org.nac.xpathqs.core.reflection

import org.nac.xpathqs.core.selector.Block
import org.nac.xpathqs.core.selector.Selector
import org.nac.xpathqs.core.selector.SelectorProps

object Page_WithBase: Block(
    Selector(
        props = SelectorProps(tag = "base")
    )
) {
    val s1 = Selector(props = SelectorProps(tag = "s1"))
}

object Page_NoBase: Block() {
    val s1 = Selector(props = SelectorProps(tag = "s1"))
}