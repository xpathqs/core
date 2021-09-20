/*
 * Copyright (c) 2021 XPATH-QS
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

package org.xpathqs.core.selector.block

import org.xpathqs.core.reflection.freeze
import org.xpathqs.core.reflection.isObject
import org.xpathqs.core.reflection.setBase
import org.xpathqs.core.reflection.setProps
import org.xpathqs.core.selector.NullSelector
import org.xpathqs.core.selector.base.BaseSelector
import org.xpathqs.core.selector.base.BaseSelectorProps
import org.xpathqs.core.selector.base.ISelector
import org.xpathqs.core.selector.extensions.core.clone
import org.xpathqs.core.selector.group.GroupSelector
import org.xpathqs.core.selector.result.ResultSelector
import org.xpathqs.core.selector.selector.Selector
import org.xpathqs.core.selector.selector.SelectorProps
import org.xpathqs.core.selector.xpath.XpathSelector

/**
 * Base class for the Selector container objects
 */
open class Block(
    base: ISelector = NullSelector(),
    props: SelectorProps = SelectorProps(),
    selectorsChain: ArrayList<BaseSelector> = ArrayList(),
    name: String = "",
    isBlank: Boolean = true
) : GroupSelector(
    base = base,
    props = props,
    name = name,
    selectorsChain = selectorsChain
) {
   /**
    * Points that the current selector was not initialized based on
    * some other selector via constructor
    */
    var isBlank: Boolean = isBlank
        internal set

    /**
     * Points to the original object-class instance.
     * The [GroupSelector.clone] method will update the [base] link of each
     * [children] selectors. And will restore it in the [fixChildrenSelectors] method.
     * Such weird implementation was introduces because of Kotlin object-class members implementation.
     * They all compiled to the static java objects, and there is no way to clone the whole object-class member,
     * with inner fields.
     * @see [GroupSelector.clone]
     */
    internal var originBlock: ISelector = NullSelector()
    internal var originFieldProps: BaseSelectorProps = BaseSelectorProps()

    /**
     * List of selector-based members of the current Block
     */
    internal var children: Collection<BaseSelector> = emptyList()

    /**
     * Initialization based on [Selector]
     */
    constructor(sel: Selector) : this(
        isBlank = false,
        base = sel.base.clone(),
        props = sel.props.clone(),
        selectorsChain = arrayListOf(sel.clone())
    )

    /**
     * Initialization based on [XpathSelector]
     */
    constructor(sel: XpathSelector) : this(
        isBlank = false,
        base = sel.base.clone(),
        props = SelectorProps(
            prefix = "",
            tag = sel.tag,
            args = sel.props.args
        ),
        selectorsChain = arrayListOf(sel.clone())
    )

    /**
     * Initialization based on [GroupSelector]
     */
    constructor(sel: GroupSelector) : this(
        isBlank = false,
        base = sel.base.clone(),
        props = SelectorProps(
            prefix = "",
            tag = sel.tag,
            args = sel.props.args
        ),
        selectorsChain = sel.selectorsChain
    )

    /**
     * Will copy properties from the XpathSelector
     *
     * Require - Xpath's selector properties should be copied
     * @sample [org.xpathqs.core.selector.block.CopyFromTest.copyFromXpathSelector]
     */
    protected fun copyFrom(sel: XpathSelector) {
        isBlank = false
        setBase(sel.base.clone())
        setProps(
            SelectorProps(
                prefix = "",
                tag = sel.tag,
                args = sel.props.args
            )
        )
        selectorsChain = arrayListOf(sel.clone())
    }

    /**
     * Will copy properties from the Selector
     *
     * Require - Selector's properties should be copied
     * @sample [org.xpathqs.core.selector.block.CopyFromTest.copyFromSelector]
     */
    protected fun copyFrom(sel: Selector) {
        isBlank = false
        setBase(sel.base.clone())
        setProps(sel.props.clone())
        selectorsChain = arrayListOf(sel.clone())
    }

    /**
     * Will copy properties from the GroupSelector
     *
     * Require - GroupSelector's properties should be copied
     * @sample [org.xpathqs.core.selector.block.CopyFromTest.copyFromGroupSelector]
     */
    protected fun copyFrom(sel: GroupSelector) {
        isBlank = false
        setBase(sel.base.clone())
        setProps(
            SelectorProps(
                prefix = "",
                tag = sel.tag,
                args = sel.props.args
            )
        )
        selectorsChain = arrayListOf(sel.clone())
    }

    /**
     * Will copy properties from the GroupSelector
     *
     * Require - GroupSelector's properties should be copied
     * @sample [org.xpathqs.core.selector.block.CopyFromTest.copyFromGroupSelector]
     */
    protected fun copyFrom(sel: ResultSelector) {
        isBlank = false
        setBase(sel.base.clone())
        setProps(
            SelectorProps(
                prefix = "",
                tag = sel.tag,
                args = sel.props.args
            )
        )
        selectorsChain = arrayListOf(sel.clone())
    }

    /**
     * Get Xpath query and restore link of [children] selectors
     * to the origin object
     *
     * Require #1 - Selector properties should reverted
     * @sample [org.xpathqs.core.selector.block.BlockSelectorXpathTest.r1_toXpath]
     */
    override fun toXpath(): String {
        val res = super.toXpath()
        revertToOrigin()
        return res
    }

    /**
     * No [revertToOrigin] for logging for logging purposes
     *
     * Require #1 - Selector properties should not be reverted when call for logging
     * @sample [org.xpathqs.core.selector.block.BlockSelectorXpathTest.r1_xpath]
     */
    override val xpath: String
        get() = super.toXpath()

    protected fun revertToOrigin() {
        if (originBlock !is NullSelector && this.isObject() && !isCalledForLogging()) {
            fixChildrenSelectors()
            fixParentField()
        }
    }

    private fun isCalledForLogging(): Boolean =
        Thread.currentThread().stackTrace.find {
            it.methodName == "xpath" ||
                    it.methodName == "getXpath"
        } != null

    /**
     * Restore [base] link of [children] selectors
     */
    protected fun fixChildrenSelectors() {
        children.forEach {
            it.setBase(originBlock)
            (it.field?.get(this) as BaseSelector).setBase(originBlock)
        }

        freeze()
    }

    /**
     * Restore origin field value for parent selector
     */
    private fun fixParentField() {
        (field?.get(base) as? Block)?.setProps(originFieldProps)
    }

    fun copyProps(other: Block?) {
        if (other == null) {
            return
        }
        selectorsChain = other.selectorsChain
        setProps(other.props)
    }

    /**
     * Callback invoked during [org.xpathqs.core.reflection.SelectorParser.parse] method
     */
    open fun afterReflectionParse() {}
}