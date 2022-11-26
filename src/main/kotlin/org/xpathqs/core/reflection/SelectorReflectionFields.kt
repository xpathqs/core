/*
 * Copyright (c) 2022 XPATH-QS
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
import org.xpathqs.core.selector.block.Block
import java.lang.reflect.Field
import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.superclasses
import kotlin.reflect.javaType
import kotlin.reflect.jvm.isAccessible

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
        innerSelectorProps.filter {
            it.call(rootObj) !is NullSelector
        }.map {
            (it.call(rootObj) as BaseSelector).setName(it.name)
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
    val declaredProps: Collection<KProperty<*>> by lazy {
        val res = ArrayList<KProperty<*>>()

        var cls = rootObj::class
        if(cls.isScanAvailable) {
            res.addAll(cls.declaredMemberProperties)


            while (cls.superclasses.first().isSelectorSubtype() && cls.isScanAvailable) {
                (cls.superclasses.firstOrNull() as? KClass<out BaseSelector>)?.let {
                    res.addAll(it.declaredMemberProperties)
                    cls = it
                } ?: Object::class
            }
        }

        removeUnnecessary(res)
    }

    /**
     * Filter [declaredProps] result by [BaseSelector] fields only
     */
    val innerSelectorProps: Collection<KProperty<*>>
        by lazy {
            val res = ArrayList<KProperty<*>>()

            declaredProps.filter { it.name != "base"}.forEach {
                if ((it.returnType.classifier as KClass<*>).isSelectorSubtype()) {
                    it.isAccessible = true
                    res.add(it)
                }
            }

            removeUnnecessary(res)
        }

    /**
     * Returns a collection of `Class` elements which are inherited from the [BaseSelector]
     */
    val innerObjectClasses: Collection<KClass<*>>
            by lazy {
                val res = ArrayList<KClass<*>>()
                val rootCls = rootObj::class
                if(rootCls.isScanAvailable) {
                    if (rootCls.simpleName != BaseSelector::class.simpleName) {
                        rootCls.nestedClasses.forEach {
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
    private fun removeUnnecessary(fields: Collection<KProperty<*>>) = fields
        .filter {
            it.name != "INSTANCE" //remove object-class instances
                    && it.name != "Companion" //remove object-class instances
                    && !it.name.contains("\$")  //remove fields which was added dynamically
                    && it.isScanAvailable //remove fields annotated with "@NoScan
        }.distinctBy {
            it.name
        }

    private val KProperty<*>.isScanAvailable: Boolean
        get() = this.findAnnotation<NoScan>() == null

    private val KClass<*>.isScanAvailable: Boolean
        get() = this.findAnnotation<NoScan>() == null
}