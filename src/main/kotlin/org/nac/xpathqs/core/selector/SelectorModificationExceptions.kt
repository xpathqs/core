package org.nac.xpathqs.core.selector

import org.nac.xpathqs.core.reflection.setProps

fun <T: Selector>T.tag(value: String): T {
    val res = this.clone()

    res.setProps(props.copy(tag = value))

    return res
}