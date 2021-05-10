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