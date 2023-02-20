/*
 * Copyright (c) 2023 XPATH-QS
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

import org.xpathqs.core.annotations.NoScan
import org.xpathqs.core.selector.NullSelector
import org.xpathqs.core.selector.base.BaseSelector
import org.xpathqs.core.selector.base.ISelector
import org.xpathqs.core.selector.block.Block
import org.xpathqs.core.util.SelectorFactory.tagSelector
import java.lang.Exception
import java.lang.reflect.Field
import kotlin.reflect.KClass

/**
 * Utility class for interaction with [BaseSelector] fields via Reflection
 * @param rootObj object for interaction
 * @sample org.xpathqs.core.reflection.SelectorReflectionFieldsTest
 */
class JavaSelectorReflectionFields(
    private val rootObj: BaseSelector
) {
    /**
     * Returns collection of [BaseSelector]s inner objects of [rootObj]
     */
    val innerSelectors: Collection<BaseSelector> by lazy {
        innerSelectorFields.filter {
            it.get(rootObj) !is NullSelector
        }.map {
            (it.get(rootObj) as BaseSelector).setName(it.name)
        }
    }

    /**
     * Returns collection of [Block]s inner objects of [rootObj]
     */
    val innerBlocks: Collection<Block> by lazy {
        innerObjectClasses
            .filter {
                it.isObject()
            }.map {
                it.getObject()
            }
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
                if(cls.isScanAvailable) {
                    res.addAll(cls.declaredFields)

                    while (cls.superclass.isSelectorSubtype() && cls.isScanAvailable) {
                        cls = cls.superclass as Class<out BaseSelector>
                        res.addAll(cls.declaredFields)
                    }
                }

                removeUnnecessary(res)
            }

    /**
     * Filter [declaredFields] result by [BaseSelector] fields only
     */
    val innerSelectorFields: Collection<Field>
            by lazy {
                val res = ArrayList<Field>()

                declaredFields.filter { it.name != "base"}.forEach {
                    if (it.type.isSelectorSubtype()) {
                        it.isAccessible = true
                        res.add(it)
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
                val rootCls = rootObj::class.java
                if(rootCls.isScanAvailable) {
                    if (rootCls.simpleName != BaseSelector::class.java.simpleName) {
                        rootCls.declaredClasses.forEach {
                            if (it.isSelectorSubtype()) {
                                if(it.isScanAvailable) {
                                    res.add(it)
                                }
                            }
                        }
                    }
                }
                res
            }

    /**
     * Filter Unnecessary fields
     */
    private fun removeUnnecessary(fields: Collection<Field>) = fields
        .filter {
            it.name != "INSTANCE" //remove object-class instances
                    && !it.name.contains("\$")  //remove fields which was added dynamically
                    && it.isScanAvailable //remove fields annotated with "@NoScan
        }
        .distinctBy { it.name }

    private val Field.isScanAvailable: Boolean
        get() = !this.isAnnotationPresent(NoScan::class.java)

    private val Class<*>.isScanAvailable: Boolean
        get() = !this.isAnnotationPresent(NoScan::class.java)

    internal fun Any.isObject(): Boolean {
        if (this is Class<*>) {
            return this.declaredFields
                .find {
                    it.name == "INSTANCE"
                } != null
        }
        return this.javaClass.declaredFields
            .find {
                it.name == "INSTANCE"
            } != null
    }

    /**
     * Check if object is a subtype of [Block]
     *
     * Requirements:
     * #1 - true when object is a subtype of [Block] but not Block directly
     * @sample [org.xpathqs.core.reflection.extensions.ReflectionExtensionsIsBlockSubtypeTests.r1]
     * #2 - false for all others cases
     */
    internal fun Any.isBlockSubtype(): Boolean {
        return this is Block && this.javaClass.superclass.simpleName != "Block"
    }
    /**
     * Get an Object instance of [Block] when class is an Object-class inherited from block
     * @sample org.xpathqs.core.reflection.extensions.ReflectionExtensionsGetObjectTests
     */
    internal fun Class<*>.getObject(): Block {
        val instanceField = this.declaredFields
            .find { it.name == "INSTANCE" } ?: throw IllegalArgumentException(
            "Provided class ${this.name} doesn't used as an object-class"
        )

        return instanceField.get(null) as? Block ?: throw IllegalArgumentException(
            "Provided class $name is not inherited from the Block class"
        )
    }

    /**
     * Check class for having [ISelector] as an inherited parent
     */
    @Suppress("ReturnCount")
    internal fun Class<*>.isSelectorSubtype(): Boolean {
        if (this == BaseSelector::class.java
            || this == NullSelector::class.java
            || this == ISelector::class.java) {
            return true
        }
        if (this.superclass == null) {
            return false
        }
        return ISelector::class.java.isAssignableFrom(this.superclass)
                || this.isAssignableFrom(ISelector::class.java)
    }
}