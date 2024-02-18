/*
 * Copyright (c) 2024 XPATH-QS
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

import org.xpathqs.core.exception.ReflectionException
import org.xpathqs.core.selector.args.SelectorArgs
import org.xpathqs.core.selector.base.BaseSelector
import org.xpathqs.core.selector.base.BaseSelectorProps
import org.xpathqs.core.selector.base.ISelector
import org.xpathqs.core.selector.base.SelectorState
import java.lang.reflect.Field
import kotlin.reflect.KProperty

/**
 * API to work with [BaseSelector] via Reflection
 * @param obj - object for modification
 * @sample org.xpathqs.core.reflection.SelectorReflectionTest
 */
internal class SelectorReflection(
    private val obj: BaseSelector,
) {
    /**
     * Sets the property value to the [obj]
     * @param name of class property
     * @param value to set
     */

    fun findField(name: String): Field {
        var result: Field? = null

        var cls: Class<*> = obj::class.java
        while(result == null) {
            result = cls.declaredFields.find { it.name == name }
            cls = cls.superclass
        }

        return result
    }

    fun setProp(name: String, value: Any?): SelectorReflection {
        findField(name).let { member ->
            member.isAccessible = true
            //Kotlin can't cast KProperty to KMutableProperty
            member.set(obj, value)
        }
        return this
    }

    /**
     * Set <pre>name</pre> field of the [obj]
     */
    fun setName(value: String) = setProp(BaseSelector::name.name, value)

    /**
     * Set <pre>name</pre> field of the [obj]
     */
    fun setFullName(value: String) = setProp(BaseSelector::fullName.name, value)

    /**
     * Set <pre>base</pre> field of the [obj]
     */
    fun setBase(value: ISelector) = setProp(BaseSelector::base.name, value)

    /**
     * Set <pre>props</pre> field of the [obj]
     */
    fun setProps(value: BaseSelectorProps) = setProp(BaseSelector::props.name, value)

    /**
     * Set <pre>props.args</pre> field of the [obj]
     */
    fun setArgs(args: SelectorArgs) = BaseSelectorProps::args.toField().set(obj.props, args)

    /**
     * Mark [obj] as freeze
     */
    fun freeze() = setProp(BaseSelector::state.name, SelectorState.FREEZE)

    /**
     * Mark [obj] as cloned
     */
    fun cloned() = setProp(BaseSelector::state.name, SelectorState.CLONED)

    /**
     * Set [BaseSelector.annotations] field of the [BaseSelector]
     * @param annotations collection of annotation classes provided via reflection
     */
    fun setAnnotations(annotations: Collection<Annotation>) = setProp(BaseSelector::annotations.name, annotations)

    /**
     * Sets [BaseSelector.property]
     */
    fun setProperty(prop: KProperty<*>?) = setProp(BaseSelector::property.name, prop)

}