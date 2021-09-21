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

package org.xpathqs.core.selector.base

import org.xpathqs.core.selector.NullSelector
import java.lang.reflect.Field
import kotlin.reflect.KClass

/**
 * Base class for the Selectors
 * @param state of the Selector
 * @param base link to the root element
 * @param name name of the selector (used for logging purposes)
 * @param props properties for building xpath queries
 * @param annotations list of annotations for selector
 * @param field associated field of the root object.
 *      It may be NULL, when there is no root object
 */
abstract class BaseSelector(
    internal val state: SelectorState = SelectorState.INIT,

    val base: ISelector = NullSelector(),
    override val name: String = "",

    internal open val props: BaseSelectorProps = BaseSelectorProps(),
    val annotations: Collection<Annotation> = emptyList(),

    internal val field: Field? = null
) : ISelector {

    /**
     * Get selector's tag or an empty string
     * if tag is not applicable to the Selector's type
     */
    abstract val tag: String

    /**
     * Build selector's xpath
     */
    override fun toXpath(): String {
        val b = base.toXpath()
        val p = props.toXpath()
        if(b.isNotEmpty() && !p.startsWith("/")) {
            return "$b//$p"
        }
        return b + p
    }

    /**
     * @return selector's string representation
     *
     * Require #1 - when [name] is not empty, it should be returned
     * @sample org.xpathqs.core.selector.base.BaseSelectorToStringTest.r1_notEmptyName
     *
     * Require #2 - when [name] is empty, [toXpath] should be returned
     * @sample org.xpathqs.core.selector.base.BaseSelectorToStringTest.r2_emptyName
     */
    override fun toString(): String {
        return name.ifEmpty {
            xpath
        }
    }
}