package org.xpathqs.core.constants

import org.xpathqs.core.util.PropertyFacade
import java.io.InputStream

/**
 * Class for storing global properties
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

    fun update(other: CoreGlobalProps): CoreGlobalProps {
        this.props = other.props
        return this
    }

    //inner text attribute for the Web-based selectors
    val TEXT_ARG: String
        get() = props["constants.text_arg"] as? String ?: "text()"

    //inner text attribute for the Web-based selectors
    val ID_ARG: String
        get() = props["constants.id_arg"] as? String ?: "@id"
}

object Global : CoreGlobalProps()