package org.nachg.xpathqs.core.reflection

import org.nachg.xpathqs.core.selector.Block
import org.nachg.xpathqs.core.selector.Selector
import org.nachg.xpathqs.core.selector.SelectorProps

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

object Page_WithBase_AndInnerObject: Block(
    Selector(
        props = SelectorProps(tag = "base")
    )
) {
    val s1_base = Selector(props = SelectorProps(tag = "base_tag"))

    object Inner: Block(
        Selector(
            props = SelectorProps(tag = "inner")
        )
    ) {
        val s1_inner = Selector(props = SelectorProps(tag = "inner_tag"))
    }
}