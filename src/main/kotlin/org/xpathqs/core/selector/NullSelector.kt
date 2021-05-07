package org.xpathqs.core.selector

import org.xpathqs.core.selector.base.ISelector

class NullSelector : ISelector {
    override fun toXpath() = ""
    public override fun clone(): NullSelector {
        return this
    }
}