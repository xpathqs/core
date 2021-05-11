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

package org.xpathqs.core.selector.base

import org.xpathqs.core.selector.NullSelector

/**
 * Base class for the Selectors
 * @param state of the Selector
 * @param base link to the root element
 * @param name name of the selector (used for logging purposes)
 * @param props properties for building xpath queries
 */
abstract class BaseSelector(
    internal val state: SelectorState = SelectorState.INIT,

    internal val base: ISelector = NullSelector(),
    override val name: String = "",

    internal open val props: BaseSelectorProps = BaseSelectorProps(),
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
        return base.toXpath() + props.toXpath()
    }

    /**
     * returns selector's name
     */
    override fun toString(): String {
        return name
    }
}