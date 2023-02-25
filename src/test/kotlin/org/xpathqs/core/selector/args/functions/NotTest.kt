/*
 * Copyright (c) 2023 XPATH-QS
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

package org.xpathqs.core.selector.args.functions

import io.kotest.core.spec.style.AnnotationSpec
import org.xpathqs.core.selector.args.KVSelectorArg
import org.xpathqs.core.selector.args.ValueArg
import org.xpathqs.core.selector.args.decorators.CommaDecorator
import org.xpathqs.core.selector.args.decorators.ContainsDecorator
import org.xpathqs.core.selector.extensions.core.get
import org.xpathqs.core.selector.selector.ancestor
import org.xpathqs.core.util.SelectorFactory.tagSelector
import org.xpathqs.xpathShouldBe

class NotTest : AnnotationSpec() {

    @Test
    fun argumentsCompose() {
        tagSelector("button")[
            Not(
                ValueArg(
                    tagSelector("div").ancestor()[
                        ContainsDecorator(
                            CommaDecorator(
                                KVSelectorArg("@style", "display:none")
                            )
                        )
                    ]
                )
            )
        ][
            Not(
                ValueArg(
                    tagSelector("div").ancestor()[
                        ContainsDecorator(
                            CommaDecorator(
                                KVSelectorArg("@style", "display: none")
                            )
                        )
                    ]
                )
            )
        ].xpathShouldBe(
            "//button[not(ancestor::div[contains(@style, 'display:none')]) " +
                    "and not(ancestor::div[contains(@style, 'display: none')])]"
        )
    }
}