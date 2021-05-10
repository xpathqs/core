/*
 * Copyright (c) 2021 Nikita A. Chegodaev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.xpathqs.core.reflection

import org.xpathqs.core.selector.Block
import org.xpathqs.core.selector.base.BaseSelector
import org.xpathqs.core.selector.base.BaseSelectorProps
import org.xpathqs.core.selector.base.ISelector
import org.xpathqs.core.selector.group.GroupSelector
import java.lang.reflect.Field
import kotlin.jvm.internal.CallableReference
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

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
    if (this == BaseSelector::class.java) {
        return true
    }
    return BaseSelector::class.java.isAssignableFrom(this.superclass)
            || this.isAssignableFrom(BaseSelector::class.java)
}

internal fun <T : BaseSelector> T.setName(name: String): T {
    SelectorReflection(this)
        .setName(name)
    return this
}

internal fun <T : BaseSelector> T.setBase(base: ISelector): T {
    SelectorReflection(this)
        .setBase(base)
    return this
}

internal fun <T : BaseSelector> T.setProps(props: BaseSelectorProps): T {
    SelectorReflection(this)
        .setProps(props)
    return this
}

internal fun <T : BaseSelector> T.freeze(): T {
    SelectorReflection(this).freeze()
    return this
}

internal fun <T : GroupSelector> T.freeze(): T {
    SelectorReflection(this).freeze()
    this.selectorsChain.forEach {
        it.freeze()
    }
    return this
}

internal fun <T : BaseSelector> T.cloned(): T {
    SelectorReflection(this).cloned()
    return this
}

internal fun Block.setBlank(value: Boolean) {
    SelectorReflection(this).setProp("isBlank", value)
}

internal fun KProperty<*>.toField(): Field {
    val cls = (((this as CallableReference).owner) as KClass<*>).java
    return cls.declaredFields.find {
        it.name == this.name
    }!!.apply {
        isAccessible = true
    }
}