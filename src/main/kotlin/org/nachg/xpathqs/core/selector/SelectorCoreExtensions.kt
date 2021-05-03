package org.nachg.xpathqs.core.selector

import org.nachg.xpathqs.core.reflection.*

fun <T: ISelector>T.clone(): T {
    if(this is Selector) {
        return clone()
    }
    return this
}

fun <T: Selector>T.clone(): T {
    if(this.state != SelectorState.FREEZE) {
        return this
    }

    val newObj = this.newInstance()

    newObj.setName(this.name)
    newObj.setBase(this.base.clone())
    newObj.setProps(this.props.copy())

    if(newObj is Block) {
        this as Block

        val children = ArrayList<Selector>()
        children.addAll(this.children)

        newObj.setBlank(this.isBlank)
        newObj.originBlock = this
        newObj.children = children
        if(newObj.isObject()) {
            this.children.forEach {
                it.setBase(newObj)
            }
        }
    }

    newObj.cloned()
    return newObj
}

@Suppress("UNCHECKED_CAST")
fun <T: Selector>T.newInstance(): T {
    val c = this::class.java.declaredConstructors.find {
        it.parameterCount == 0
    } ?: throw IllegalArgumentException("Selector doesn't have a default constructor")

    c.isAccessible = true
    return c.newInstance() as T
}