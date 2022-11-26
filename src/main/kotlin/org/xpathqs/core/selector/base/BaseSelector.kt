/*
 * Copyright (c) 2022 XPATH-QS
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
import kotlin.reflect.KProperty

/**
 * Base class for the Selectors
 * @param state of the Selector
 * @param base link to the root element
 * @param name name of the selector (used for logging purposes)
 * @param props properties for building xpath queries
 * @param annotations list of annotations for selector
 * @param property associated field of the root object.
 *      It may be NULL, when there is no root object
 */
abstract class BaseSelector(
    internal val state: SelectorState = SelectorState.INIT,

    val base: ISelector = NullSelector(),
    override val name: String = "",

    internal open val props: BaseSelectorProps = BaseSelectorProps(),
    val annotations: Collection<Annotation> = emptyList(),

    val property: KProperty<*>? = null,
    val noBase: Boolean = false,
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
        return mergeXpath(
            if(noBase) "" else base.toXpath(),
            props.toXpath()
        )
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

    /**
     * A holder collection for some custom properties
     * which may be used for the extending the functionality via extension-functions
     *
     * Require #1 - map should not be cloned. It purposes only for holding some configuration constants
     * @sample org.xpathqs.core.selector.base.BaseSelectorCustomPropsMapTest.r1
     */
    val customPropsMap = HashMap<String, Any?>()

    companion object {
        fun mergeXpath(xp1: String, xp2: String): String {
            if(xp1.isNotEmpty()
                && !xp2.startsWith("/")
                && !xp2.startsWith("[")
            ) {
                return "$xp1//$xp2"
            }
            return xp1 + xp2
        }
    }
}