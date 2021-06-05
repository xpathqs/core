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

package org.xpathqs.core.model

import org.xpathqs.core.selector.base.BaseSelector
import java.lang.reflect.Field


class AssociationFinder(
    protected val fields: Collection<Field>,
    protected val selectors: Collection<BaseSelector>,
    protected val associations: Collection<IModelAssociation>
) {
    protected lateinit var unusedFields: HashSet<Field>

    val mappings: Collection<ModelAssociation>
        get() {
            unusedFields = HashSet()

            return scanFields(associations.first())
        }

    private fun scanFields(association: IModelAssociation): ArrayList<ModelAssociation> {
        val res = ArrayList<ModelAssociation>()

        fields.forEach {
            val sel = getAssociation(association, it)
            if (sel != null) {
                it.isAccessible = true
                unusedFields.remove(it)
                res.add(
                    ModelAssociation(
                        sel,
                        it
                    )
                )
            } else {
                unusedFields.add(it)
            }
        }

        return res
    }

    private fun getAssociation(association: IModelAssociation, field: Field) =
        selectors.find {
            association.isSelectorMatchToField(field, it)
        }

}