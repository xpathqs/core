package org.nachg.xpathqs.core.selector

import org.nachg.xpathqs.core.util.PropertyFacade
import java.io.InputStream

/**
 * Class for storing global properties
 */
open class CoreGlobalProps(
    protected var props: Map<String, Any>
        = mapOf("constants.text_arg" to "text()")
) {
    constructor(loader: PropertyFacade): this(loader.parse())
    constructor(input: InputStream): this(PropertyFacade(input))
    constructor(resourcePath: String): this(
        PropertyFacade(
            CoreGlobalProps::class.java.classLoader.getResource(resourcePath)?.openStream() ?:
            throw IllegalArgumentException("'$resourcePath' Resource can't be found")
        )
    )

    //inner text attribute for the Web-based selectors
    internal val TEXT_ARG: String
        get() = props["constants.text_arg"] as String

    fun update(other: CoreGlobalProps) {
        this.props = other.props
    }
}

internal object Global: CoreGlobalProps()