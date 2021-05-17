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

package org.xpathqs.core.selector.selector

import org.xpathqs.core.constants.Global
import org.xpathqs.core.selector.args.SelectorArgs
import org.xpathqs.core.selector.base.BaseSelectorProps

/**
 * [Selector] properties class
 * @param prefix - string before [tag]
 * @param tag - string after [prefix] and before [args]
 */
open class SelectorProps(
    val prefix: String = "//",
    val tag: String = "*",
    args: SelectorArgs = SelectorArgs()
) : BaseSelectorProps(args) {

    /**
     * Get Xpath string based on [prefix] + [tag] + [args]
     */
    override fun toXpath(): String {
        val tag = if (Global.UPPER_CASE_FOR_TAG) this.tag.uppercase() else this.tag
        return "$prefix$tag${args.toXpath()}"
    }

    /**
     * Clone current object with ability to overriding
     */
    fun clone(
        prefix: String = this.prefix,
        tag: String = this.tag,
        props: SelectorArgs = this.args
    ): SelectorProps {
        return SelectorProps(prefix, tag, props.clone())
    }

    /**
     * Clone current object
     */
    override fun clone(): SelectorProps {
        return SelectorProps(prefix, tag, args.clone())
    }
}
