package org.nac.xpathqs.core.reflection

import org.nac.xpathqs.core.selector.Selector
import java.lang.reflect.Field

class SelectorReflectionFields(
    private val rootObj: Selector
) {
    val innerSelectors: Collection<Selector> by lazy {
        innerSelectorFields.map { it.get(rootObj) as Selector }
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

            if(rootObj::class.java.simpleName != "Selector") {
                rootObj::class.java.declaredFields.forEach {
                    if(it.type.isSelectorSubtype()) {
                        res.add(it)
                    }
                }
            }

            removeUnnecessary(res)
        }

    private fun removeUnnecessary(fields: Collection<Field>)
        = fields.filter { it.name != "INSTANCE" }
}