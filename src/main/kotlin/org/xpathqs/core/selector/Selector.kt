/*
 * Copyright (c) 2026 XPATH-QS
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

package org.xpathqs.core.selector

import org.xpathqs.core.selector.base.SelectorState
import org.xpathqs.core.selector.selector.SelectorProps

/**
 * This is the main 'building-block' class of Selectors interaction.
 * @param props extended properties for building xpath-queries
 * @sample org.xpathqs.core.selector.selector.SelectorTest
 */
open class Selector(
    state: SelectorState = SelectorState.INIT,
    base: ISelector = NullSelector(),
    name: String = "",
    fullName: String = "",
    override val props: SelectorProps = SelectorProps()
) : BaseSelectorImpl(
    state = state,
    base = base,
    name = name,
    fullName = fullName,
    props = props,
) {
    /**
     * Returns tag property value
     * @sample org.xpathqs.core.selector.selector.SelectorTest.tag
     */
    override val tag: String
        get() = props.tag

    /**
     * Returns Selector prefix
     * @sample org.xpathqs.core.selector.selector.SelectorTest.prefix
     */
    val prefix: String
        get() = props.prefix
}