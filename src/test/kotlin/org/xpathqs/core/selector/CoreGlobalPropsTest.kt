/*
 * Copyright (c) 2022 XPATH-QS
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

package org.xpathqs.core.selector

import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import org.xpathqs.core.constants.CoreGlobalProps
import org.xpathqs.core.constants.Global


class CoreGlobalPropsTest : AnnotationSpec() {

    @Test
    fun checkTextPropForEmptyMap() {
        CoreGlobalProps(emptyMap()).TEXT_ARG shouldBe "text()"
    }

    @Test
    fun checkIdPropForEmptyMap() {
        CoreGlobalProps(emptyMap()).ID_ARG shouldBe "@id"
    }

    @Test
    fun checkDefaultTextProp() {
        CoreGlobalProps().TEXT_ARG shouldBe "text()"
    }

    @Test
    fun checkDefaultIdProp() {
        CoreGlobalProps().ID_ARG shouldBe "@id"
    }

    @Test
    fun checkDefaultIdPropForGlobal() {
        Global.ID_ARG shouldBe "@id"
    }

    @Test
    fun checkDefaultTextPropForGlobal() {
        Global.TEXT_ARG shouldBe "text()"
    }

    @Test
    fun checkOverrideTextProp() {
        CoreGlobalProps(
            mapOf("constants.text_arg" to "@text")
        ).TEXT_ARG shouldBe "@text"
    }

    @Test
    fun checkUpdateOfAProperty() {
        CoreGlobalProps(
            mapOf("constants.text_arg" to "@text")
        ).update(
            CoreGlobalProps(
                mapOf("constants.text_arg" to "text()")
            )
        ).TEXT_ARG shouldBe "text()"
    }

    @Test
    fun checkAbilityToLoadFromResources() {
        CoreGlobalProps(
            "config/config.yml"
        ).TEXT_ARG shouldBe "@text_test"
    }

    @Test
    fun incorrectPathShouldThrowAnException() {
        shouldThrowExactly<IllegalArgumentException> {
            CoreGlobalProps(
                "config/config_incorrect.yml"
            ).TEXT_ARG
        }
    }
}
