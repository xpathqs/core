package org.nachg.xpathqs.core.selector

import org.nachg.xpathqs.core.reflection.setProps

fun <T: Selector>T.tag(value: String): T {
    val res = this.clone()

    res.setProps(props.clone(tag = value))

    return res
}