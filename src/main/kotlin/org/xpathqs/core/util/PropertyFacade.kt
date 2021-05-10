package org.xpathqs.core.util

import org.yaml.snakeyaml.Yaml
import java.io.InputStream

class PropertyFacade(
    private val stream: InputStream
) {
    private val yaml = Yaml()

    fun parse() =
        flatMap(
            yaml.load(stream)
        )

    @Suppress("UNCHECKED_CAST")
    private fun flatMap(map: Map<String, Any>, prefix: String = ""): HashMap<String, String> {
        val res = HashMap<String, String>()

        map.forEach { (k, v) ->
            if (v is String) {
                res[prefix + k] = v
            } else if (v is Map<*, *>) {
                res.putAll(
                    flatMap(
                        v as Map<String, Any>,
                        "$prefix$k."
                    )
                )
            }
        }

        return res
    }
}