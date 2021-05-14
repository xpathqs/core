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
import java.lang.reflect.Field

/**
 * Utility class for interaction with [BaseSelector] fields via Reflection
 * @param rootObj object for interaction
 * @sample org.xpathqs.core.reflection.SelectorReflectionFieldsTest
 */
class SelectorReflectionFields(
    private val rootObj: BaseSelector
) {
    /**
     * Returns collection of [BaseSelector]s inner objects of [rootObj]
     */
    val innerSelectors: Collection<BaseSelector> by lazy {
        innerSelectorFields.map { it.get(rootObj) as BaseSelector }
    }

    /**
     * Returns collection of [Block]s inner objects of [rootObj]
     */
    val innerBlocks: Collection<Block> by lazy {
        innerObjectClasses.map { it.getObject() }
    }

    /**
     * Returns collection of `Field`s of [rootObj] class and all subclasses
     * until [BaseSelector]
     */
    @Suppress("UNCHECKED_CAST")
    val declaredFields: Collection<Field>
            by lazy {
                val res = ArrayList<Field>()

                var cls = rootObj::class.java
                res.addAll(cls.declaredFields)

                while (cls.superclass.isSelectorSubtype()) {
                    cls = cls.superclass as Class<out BaseSelector>
                    res.addAll(cls.declaredFields)
                }

                removeUnnecessary(res)
            }

    /**
     * Filter [declaredFields] result by [BaseSelector] fields only
     */
    val innerSelectorFields: Collection<Field>
            by lazy {
                val res = ArrayList<Field>()

                if (rootObj::class.java.simpleName != BaseSelector::class.java.simpleName) {
                    rootObj::class.java.declaredFields.forEach {
                        if (it.type.isSelectorSubtype()) {
                            it.isAccessible = true
                            res.add(it)
                        }
                    }
                }

                removeUnnecessary(res)
            }

    /**
     * Returns a collection of `Class` elements which are inherited from the [BaseSelector]
     */
    val innerObjectClasses: Collection<Class<*>>
            by lazy {
                val res = ArrayList<Class<*>>()

                if (rootObj::class.java.simpleName != BaseSelector::class.java.simpleName) {
                    rootObj::class.java.declaredClasses.forEach {
                        if (it.isSelectorSubtype()) {
                            res.add(it)
                        }
                    }
                }

                res
            }

    /**
     * Filter Unnecessary fields
     */
    private fun removeUnnecessary(fields: Collection<Field>) = fields
        .filter { it.name != "INSTANCE" && it.name != "\$jacocoData" }
        .distinctBy { it.name }
}