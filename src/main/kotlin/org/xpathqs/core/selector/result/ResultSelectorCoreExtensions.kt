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

package org.xpathqs.core.selector.result

import org.xpathqs.core.reflection.SelectorReflection
import org.xpathqs.core.reflection.cloned
import org.xpathqs.core.selector.BaseSelector
import org.xpathqs.core.selector.ResultSelector
import org.xpathqs.core.selector.base.SelectorState
import org.xpathqs.core.selector.base.deepClone as baseDeepClone


/**
 * @return clone of a [ResultSelector] Selector
 *
 */
@Suppress("UNCHECKED_CAST")
internal fun <T : ResultSelector> T.deepClone(): T {
    if (this.state != SelectorState.FREEZE) {
        return this
    }

    val cloned = this.baseDeepClone()
    if(wrapper is BaseSelector) {
        SelectorReflection(
            cloned as BaseSelector,
        ).setProp("wrapper", this.wrapper.cloned())
    }

    return cloned
}