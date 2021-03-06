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

package org.xpathqs.core.selector.base

import assertk.assertAll
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotSameAs
import org.junit.jupiter.api.Test
import org.xpathqs.core.selector.args.KVSelectorArg
import org.xpathqs.core.selector.args.SelectorArgs

internal class BaseSelectorPropsTest {

    @Test
    fun toXpathWithoutSelectors() {
        assertThat(
            BaseSelectorProps(
                args = SelectorArgs(
                    KVSelectorArg("@type", "'submit'")
                )
            ).toXpath()
        ).isEqualTo("[@type='submit']")
    }

    @Test
    fun clone() {
        val props = BaseSelectorProps(
            args = SelectorArgs(
                KVSelectorArg("@type", "'submit'")
            )
        )
        val cloned = props.clone()

        assertAll {
            assertThat(props)
                .isNotSameAs(cloned)

            assertThat(props.args)
                .isNotSameAs(cloned.args)
        }
    }

}