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

import org.xpathqs.core.selector.args.SelectorArgs
import org.xpathqs.core.selector.base.BaseSelector
import org.xpathqs.core.selector.base.BaseSelectorProps
import org.xpathqs.core.selector.base.ISelector
import org.xpathqs.core.selector.base.SelectorState

internal class SelectorReflection(
    private val obj: BaseSelector,
    private val srf: SelectorReflectionFields = SelectorReflectionFields(obj)
) {
    fun setProp(name: String, value: Any): SelectorReflection {
        val member = srf.declaredFields.find { it.name == name }

        if (member != null) {
            member.isAccessible = true
            member.set(obj, value)
        }

        return this
    }

    fun setName(value: String) = setProp("name", value)
    fun setBase(value: ISelector) = setProp("base", value)
    fun setProps(value: BaseSelectorProps) = setProp("props", value)
    fun setArgs(args: SelectorArgs) =
        BaseSelectorProps::args.toField().set(obj.props, args)

    fun freeze() = setProp("state", SelectorState.FREEZE)
    fun cloned() = setProp("state", SelectorState.CLONED)
}