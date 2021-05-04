package org.nachg.xpathqs.core.selector

import org.nachg.xpathqs.core.selector.base.ISelector

class NullSelector : ISelector {
    override fun toXpath() = ""
    public override fun clone(): NullSelector {
        return this
    }
}