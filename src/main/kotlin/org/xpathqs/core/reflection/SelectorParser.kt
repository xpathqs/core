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
import org.xpathqs.core.selector.NullSelector
import org.xpathqs.core.selector.base.BaseSelector
import org.xpathqs.core.selector.base.ISelector
import org.xpathqs.core.selector.group.GroupSelector

/**
 * Class for initializing Selectors names and structure via Reflection
 * @sample org.xpathqs.core.reflection.parser.ObjectWithBaseTest
 * @sample org.xpathqs.core.reflection.parser.ObjectWithoutBaseTest
 * @sample org.xpathqs.core.reflection.parser.ObjectWithBaseAndInnerObjectTest
 */
internal class SelectorParser(
    private val rootObj: Block,
    private val base: ISelector = NullSelector(),
    val srf: SelectorReflectionFields = SelectorReflectionFields(rootObj)
) {

    /**
     * Parse [Block] object with all of inner class-object and class fields
     * inherited from the [Block] class
     */
    fun parse() {
        val baseName = if (base.name.isNotEmpty()) base.name + "." else ""
        setFields(rootObj, base, baseName + rootObj::class.simpleName!!, rootObj::class.annotations)
        rootObj.children = srf.innerSelectors

        srf.innerSelectorFields.forEach {
            it.isAccessible = true
            val sel = it.get(rootObj) as BaseSelector
            setFields(sel, rootObj, rootObj.name + "." + it.name, it.annotations.toList())

            if (sel is Block) {
                SelectorParser(sel, rootObj).parse()
            }
        }

        srf.innerBlocks.forEach {
            SelectorParser(it, rootObj).parse()
        }
    }

    private fun setFields(to: BaseSelector, base: ISelector, name: String, annotations: Collection<Annotation>) {
        to.setBase(base)
        to.setName(name)
        to.setAnnotations(annotations)

        if(to is GroupSelector) {
            to.freeze()
        } else {
            to.freeze()
        }
    }
}