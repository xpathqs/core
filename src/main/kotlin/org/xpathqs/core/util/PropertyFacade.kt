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

package org.xpathqs.core.util

import org.yaml.snakeyaml.Yaml
import java.io.InputStream

/**
 * Reads the `Yaml` properties as a `FlatMap`
 */
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