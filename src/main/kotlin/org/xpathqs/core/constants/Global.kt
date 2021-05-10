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

package org.xpathqs.core.constants

import org.xpathqs.core.util.PropertyFacade
import java.io.InputStream

/**
 * Class for storing global properties
 */
open class CoreGlobalProps(
    protected var props: Map<String, Any>
    = mapOf("constants.text_arg" to "text()")
) {
    constructor(loader: PropertyFacade) : this(loader.parse())
    constructor(input: InputStream) : this(PropertyFacade(input))
    constructor(resourcePath: String) : this(
        PropertyFacade(
            CoreGlobalProps::class.java.classLoader.getResource(resourcePath)?.openStream()
                ?: throw IllegalArgumentException("'$resourcePath' Resource can't be found")
        )
    )

    fun update(other: CoreGlobalProps) {
        this.props = other.props
    }

    //inner text attribute for the Web-based selectors
    val TEXT_ARG: String
        get() = props["constants.text_arg"] as? String ?: "text()"

    //inner text attribute for the Web-based selectors
    val ID_ARG: String
        get() = props["constants.id_arg"] as? String ?: "@id"
}

object Global : CoreGlobalProps()