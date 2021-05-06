package org.nachg.xpathqs.core.reflection

import org.nachg.xpathqs.core.selector.Block
import org.nachg.xpathqs.core.selector.base.ISelector
import org.nachg.xpathqs.core.selector.selector.Selector
import org.nachg.xpathqs.core.selector.selector.SelectorProps

internal fun Any.isObject(): Boolean {
    return this.javaClass.declaredFields
        .find { it.name == "INSTANCE" } != null
}

@SuppressWarnings
internal fun Class<*>.getObject(): Block {
    return this.declaredFields
        .find { it.name == "INSTANCE" }?.get(null) as Block
}

internal fun Class<*>.isSelectorSubtype(): Boolean {
    if (this.superclass == null) {
        return false
    }
    if (this == Selector::class.java) {
        return true
    }
    return Selector::class.java.isAssignableFrom(this.superclass)
            || this.isAssignableFrom(Selector::class.java)
}

internal fun Selector.setName(name: String) {
    SelectorReflection(this)
        .setName(name)
}

internal fun Selector.setBase(base: ISelector) {
    SelectorReflection(this)
        .setBase(base)
}

internal fun Selector.setProps(props: SelectorProps) {
    SelectorReflection(this)
        .setProps(props)
}

internal fun Selector.freeze(): Selector {
    SelectorReflection(this).freeze()
    return this
}

internal fun Selector.cloned(): Selector {
    SelectorReflection(this).cloned()
    return this
}

internal fun Block.setBlank(value: Boolean) {
    SelectorReflection(this).setProp("isBlank", value)
}