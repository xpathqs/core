package org.nachg.xpathqs.core.reflection

import org.nachg.xpathqs.core.selector.Block
import org.nachg.xpathqs.core.selector.selector.Selector
import java.lang.reflect.Field

class SelectorReflectionFields(
    private val rootObj: Selector
) {
    val innerSelectors: Collection<Selector> by lazy {
        innerSelectorFields.map { it.get(rootObj) as Selector }
    }

    val innerBlocks: Collection<Block> by lazy {
        innerObjectClasses.map { it.getObject() }
    }

    @Suppress("UNCHECKED_CAST")
    val declaredFields: Collection<Field>
            by lazy {
                val res = ArrayList<Field>()

                var cls = rootObj::class.java
                res.addAll(cls.declaredFields)

                while (cls.superclass.isSelectorSubtype()) {
                    cls = cls.superclass as Class<out Selector>
                    res.addAll(cls.declaredFields)
                }

                removeUnnecessary(res)
            }

    val innerSelectorFields: Collection<Field>
            by lazy {
                val res = ArrayList<Field>()

                if (rootObj::class.java.simpleName != "Selector") {
                    rootObj::class.java.declaredFields.forEach {
                        if (it.type.isSelectorSubtype()) {
                            res.add(it)
                        }
                    }
                }

                removeUnnecessary(res)
            }

    val innerObjectClasses: Collection<Class<*>>
            by lazy {
                val res = ArrayList<Class<*>>()

                if (rootObj::class.java.simpleName != "Selector") {
                    rootObj::class.java.declaredClasses.forEach {
                        if (it.isSelectorSubtype()) {
                            res.add(it)
                        }
                    }
                }

                res
            }

    private fun removeUnnecessary(fields: Collection<Field>) = fields
        .filter { it.name != "INSTANCE" }
        .distinctBy { it.name }
}