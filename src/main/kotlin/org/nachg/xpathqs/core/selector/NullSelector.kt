package org.nachg.xpathqs.core.selector

class NullSelector : ISelector {
    override fun toXpath() = ""
    public override fun clone(): NullSelector {
        return this
    }
}