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

package org.xpathqs.core.constants

import org.xpathqs.core.util.PropertyFacade
import java.io.InputStream

/**
 * Base-Class for interaction with some of global properties which are platform-depended.
 * And which should be shared between different modules.
 * Those properties may be overridden by the code via standard inheritance.
 * Properties can be set from the resource file via [PropertyFacade] in the YAML format, by default
 */
open class CoreGlobalProps(
    protected var props: Map<String, Any> = emptyMap()
) {
    constructor(loader: PropertyFacade) : this(loader.parse())
    constructor(input: InputStream) : this(PropertyFacade(input))
    constructor(resourcePath: String) : this(
        PropertyFacade(
            CoreGlobalProps::class.java.classLoader.getResource(resourcePath)?.openStream()
                ?: throw IllegalArgumentException("'$resourcePath' Resource can't be found")
        )
    )

    /**
     * Will update current properties
     * @param other - new properties holder
     * @return self
     */
    fun update(other: CoreGlobalProps): CoreGlobalProps {
        this.props = other.props
        return this
    }

    /**
     * Text argument parameter value.
     * Used to work with XPATH-queries which are dealing with text
     * Text argument is a platform depended.
     * It is <pre>text()</pre> for the WEB and <pre>@text</pre> for the mobile
     */
    val TEXT_ARG: String
        get() = props["constants.text_arg"] as? String ?: "text()"

    /**
     * ID argument parameter value.
     * Used to work with XPATH-queries which are dealing with ID
     * ID argument is a platform depended.
     * It is <pre>@id()</pre> for the WEB and <pre>@resource-id</pre> for the mobile
     */
    val ID_ARG: String
        get() = props["constants.id_arg"] as? String ?: "@id"

    /**
     * Converts tags in [org.xpathqs.core.selector.selector.Selector.toXpath] to upper case
     * Upper case for tag is a requirement for such HTML parsers like `NekoHtml`
     */
    val UPPER_CASE_FOR_TAG: Boolean
        get() = (props["constants.upperCase"] as? String)?.toBoolean() ?: false
}

/**
 * Singleton to work with global properties inside <pre>org.xpathqs.core</pre> package classes
 */
object Global : CoreGlobalProps()