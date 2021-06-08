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

package org.xpathqs.core.reflection

import org.reflections.Reflections
import org.xpathqs.core.selector.block.Block

/**
 * Scans all [org.xpathqs.core.selector.block.Block] classes via reflection
 * @param scanner helper class to extract all object-classes
 * which has [org.xpathqs.core.selector.block.Block] as a parent
 * @see [SelectorParser]
 */
class PackageScanner(
    private val scanner: Reflections
) {

    /**
     * Scan provided package
     */
    constructor(packageName: String) : this(Reflections(packageName))

    val packageObjects: Collection<Block> by lazy {
        scanner.getSubTypesOf(Block::class.java).filter {
            it.isObject() && !it.name.contains("$")
        }.map {
            it.getObject()
        }
    }

    fun scan() {
        packageObjects.forEach {
            SelectorParser(it).parse()
        }
    }
}