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

package org.xpathqs.core.selector.args.decorators

import org.xpathqs.core.selector.args.KVSelectorArg

/**
 * Decorator for normalizing [wrapper] value
 * @param wrapper object to applying normalization
 * @param normalizeK runs normalization of [k]
 * @param normalizeV runs normalization of [v]
 *
 * Require #1 - Only [k] should be normalized by default
 * @sample org.xpathqs.core.selector.args.decorators.KVNormalizeSpaceDecoratorTest.r1
 *
 * Require #2 - When [normalizeK] and [normalizeV] set to true, [k] and [v] should be normalized
 * @sample org.xpathqs.core.selector.args.decorators.KVNormalizeSpaceDecoratorTest.r2
 *
 * Require #3 - When [normalizeK] and [normalizeV] set to false - normalization should be ignored
 * @sample org.xpathqs.core.selector.args.decorators.KVNormalizeSpaceDecoratorTest.r3
 */
class KVNormalizeSpaceDecorator(
    private val wrapper: KVSelectorArg,
    private val normalizeK: Boolean = true,
    private val normalizeV: Boolean = false
) : KVSelectorArg(wrapper.k, wrapper.v) {
    private val pattern = "normalize-space(%s)"

    override val k: String
        get() = if(normalizeK) pattern.format(super.k) else super.k

    override val v: String
        get() = if(normalizeV) pattern.format(super.v) else super.v
}