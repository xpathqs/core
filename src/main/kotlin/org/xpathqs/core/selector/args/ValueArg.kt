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

package org.xpathqs.core.selector.args

/**
 * Representation of Selector's argument value and the way of how it is combined
 * with another arguments
 *
 * @param value processed value
 * @param joinType the way of combination
 */
open class ValueArg(
    internal open val value: String = "",
    internal var joinType: JoinType = JoinType.NONE
) {
    /**
     * Build an argument XPATH to inject into selector's arguments block
     * @sample org.xpathqs.core.selector.args.SelectorArgTests
     */
    fun toXpath(): String {
        return when (joinType) {
            JoinType.NONE -> value
            JoinType.OR -> " or $value "
            JoinType.AND -> " and $value "
        }
    }

    /**
     * unique argument key
     */
    open val key: String
        get() = value

    /**
     * returns true when there is specific [JoinType]
     */
    val isNone: Boolean
        get() = joinType == JoinType.NONE
}