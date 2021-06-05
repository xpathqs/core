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

package org.xpathqs.core.selector.block

import org.xpathqs.core.reflection.isObject
import org.xpathqs.core.reflection.setBase
import org.xpathqs.core.reflection.setBlank
import org.xpathqs.core.selector.base.BaseSelector
import org.xpathqs.core.selector.extensions.clone
import org.xpathqs.core.selector.group.GroupSelector
import org.xpathqs.core.selector.group.deepClone

/**
 * @return clone of a [Block] Selector
 *
 */
@Suppress("UNCHECKED_CAST")
internal fun <T : Block> T.deepClone(): T {
    val cloned = (this as GroupSelector).deepClone() as T
    cloned.setBlank(this.isBlank)

    if (this.isObject()) {
        cloned.originBlock = this
        cloned.originFieldProps = this.props.clone()
    }

    if (!this.isObject()) {
        cloned.children = this.children.map {
            val f = it.clone()
            it.field?.set(cloned, f)
            f.setBase(cloned)
            f
        }
    } else {
        this.children.forEach {
            // val f = it.clone()
            (it.field?.get(cloned) as BaseSelector).setBase(cloned)
            it.setBase(cloned)
        }
        cloned.children = this.children
    }
    return cloned
}


/*
fun <T : GroupSelector> T.clone(): T {
   val newObj = this.deepClone()

 if (newObj is Block) {
       this as Block

       val children = ArrayList<BaseSelector>()
       this.children.forEach {
           children.add(it.setBase(newObj))
       }

       newObj.setBlank(this.isBlank)
       newObj.children = children
       newObj.originBlock = this
   }

   if(newObj.base is Block && newObj.field != null) {
       newObj as Block

       val newField = newObj.field

       newObj.originFieldProps = (newField.get(newObj.base) as Block).props.clone()

       //   BeanUtils.copyProperties(newObj.originFieldProps, newField.get(newObj.base) )
       //  removeFinalModifier(newObj.field, true)
       //    removeStaticModifier(newObj.field)
       BeanUtils.copyProperties(newField.get(newObj.base), newObj)
       //  println(newField.get(newObj.base))
       //   newObj.originFieldValue = newField.get(newObj.base) as ISelector
       //   newObj.field?.set(newObj.base, newObj)
   }

    return newObj
}
*/