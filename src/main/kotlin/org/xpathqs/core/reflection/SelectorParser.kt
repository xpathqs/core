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

package org.xpathqs.core.reflection

import org.xpathqs.core.annotations.Name
import org.xpathqs.core.annotations.SingleBase
import org.xpathqs.core.selector.NullSelector
import org.xpathqs.core.selector.base.BaseSelector
import org.xpathqs.core.selector.base.ISelector
import org.xpathqs.core.selector.base.hasAnnotation
import org.xpathqs.core.selector.block.Block
import org.xpathqs.core.selector.selector.prefix
import org.xpathqs.core.selector.group.GroupSelector
import org.xpathqs.core.selector.group.prefix
import org.xpathqs.core.selector.selector.Selector
import java.lang.reflect.Field
import kotlin.reflect.full.findAnnotation

/**
 * Class for initializing Selectors names and structure via Reflection
 * @sample org.xpathqs.core.reflection.parser.ObjectWithBaseTest
 * @sample org.xpathqs.core.reflection.parser.ObjectWithoutBaseTest
 * @sample org.xpathqs.core.reflection.parser.ObjectWithBaseAndInnerObjectTest
 */
class SelectorParser(
    private val rootObj: Block,
    private val base: ISelector = NullSelector(),
    val srf: SelectorReflectionFields = SelectorReflectionFields(rootObj)
) {

    /**
     * Parse [Block] object with all of inner class-object and class fields
     * inherited from the [Block] class
     *
     * Require #1 - after properties was initialized [Block.afterReflectionParse] callback should be invoked
     * @sample org.xpathqs.core.reflection.parser.CallbackTest.afterReflectionParse
     */
    fun parse() {
        val baseName = if (base.name.isNotEmpty()) base.name + "." else ""
        val ann = rootObj::class.findAnnotation<Name>()?.value ?: rootObj::class.simpleName!!
        val rootName = rootObj.name.ifEmpty { baseName + ann}
        setFields(
            to = rootObj,
            base = base,
            name = rootName,
            annotations = rootObj::class.annotations
        )
        rootObj.children = srf.innerSelectors

        srf.innerSelectorFields.forEach { f ->
            f.isAccessible = true
            val sel = f.get(rootObj) as BaseSelector
            val ann = (f.annotations.find {it is Name } as? Name)?.value ?: f.name
            setFields(
                to = sel,
                base = rootObj,
                name = rootObj.name + "." + ann,
                field = f
            )

            if (sel is Block) {
                SelectorParser(sel, rootObj).parse()
            }
        }

        srf.innerBlocks.forEach {
            SelectorParser(it, rootObj).parse()
        }

        rootObj.afterReflectionParse()
    }

    private fun setFields(
        to: BaseSelector,
        base: ISelector,
        name: String,
        field: Field
    ) {
        to.setField(field)
        setFields(to, base, name, field.annotations.toList())
    }

    private fun setFields(
        to: BaseSelector,
        base: ISelector,
        name: String,
        annotations: Collection<Annotation>
    ) {
        to.setBase(base)
        to.setName(name)
        to.setAnnotations(annotations)

        val forBase = (base as? BaseSelector)?.hasAnnotation(SingleBase::class) == true
        val forSelf = to.hasAnnotation(SingleBase::class) && to !is Block

        if(forSelf || forBase) {
            if(to is Selector) {
                to.prefix("/")
            } else if (to is GroupSelector) {
                to.prefix("/")
            }
        }

        if (to is GroupSelector) {
            to.freeze()
        } else {
            to.freeze()
        }
    }
}