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
 * Holder for the Selector's arguments
 * @param args map of arguments used for building xpath
 */
class SelectorArgs(
    private val args: HashMap<String, ValueArg> = HashMap()
) : Cloneable {

    /**
     * @param arg argument to add at instantiation
     */
    constructor(arg: ValueArg) : this() {
        add(arg)
    }

    /**
     * Build XPATH string based on [args]
     */
    fun toXpath(): String {
        if (args.isEmpty()) return ""

        if (args.size == 1) {
            args.values.first().joinType = JoinType.NONE
        }
        val sorted = args.values.sortedBy { it.joinType }

        val sb = StringBuilder()
        sorted.forEach {
            sb.append(it.toXpath())
        }

        val res = sb
            .toString()
            .trim()
            .replace("  ", " ")

        return "[$res]"
    }

    /**
     * Add new argument into [args]
     */
    fun add(arg: ValueArg): SelectorArgs {
        if (args.isNotEmpty() && arg.isNone) {
            arg.joinType = JoinType.AND
        }
        args[arg.key] = arg
        return this
    }

    /**
     * Add new argument into [args]
     */
    fun add(arg: String) = add(
        ValueArg(arg)
    )

    /**
     * Returns deep copy of self
     */
    @Suppress("UNCHECKED_CAST")
    public override fun clone(): SelectorArgs {
        return SelectorArgs(args.clone() as HashMap<String, ValueArg>)
    }
}