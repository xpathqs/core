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

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

import org.xpathqs.core.constants.CoreGlobalProps
import org.xpathqs.core.constants.Global
import org.xpathqs.core.selector.extensions.id
import org.xpathqs.core.selector.extensions.text
import org.xpathqs.core.util.SelectorFactory.tagSelector
import org.xpathqs.xpathShouldBe

internal class PropertyFacadeTest {

    val PATH = "config/config.yml"

    @AfterEach
    fun restoreDefaults() {
        Global.update(
            CoreGlobalProps("config/configDefault.yml")
        )
    }

    @Test
    fun parseWithInputStream() {
        assertThat(
            CoreGlobalProps(
                this::class.java.classLoader.getResource(PATH)?.openStream()
                    ?: throw IllegalArgumentException("'$PATH' Resource can't be found")
            ).TEXT_ARG
        ).isEqualTo("@text_test")
    }

    @Test
    fun parseWithFacade() {
        assertThat(
            CoreGlobalProps(
                PropertyFacade(
                    this::class.java.classLoader.getResource(PATH)?.openStream()
                        ?: throw IllegalArgumentException("'$PATH' Resource can't be found")
                )
            ).TEXT_ARG
        ).isEqualTo("@text_test")
    }

    @Test
    fun tagSelectorDefault() {
        tagSelector("div").text("hello").id("someId")
            .xpathShouldBe("//div[text()='hello' and @id='someId']")
    }

    @Test
    fun tagSelectorOverridden() {
        Global.update(
            CoreGlobalProps("config/config.yml")
        )
        tagSelector("div").text("hello").id("someId")
            .xpathShouldBe("//DIV[@text_test='hello' and @resource-id='someId']")
    }

}