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

package org.xpathqs.core.selector.selector

import io.kotest.core.spec.style.AnnotationSpec
import org.xpathqs.core.constants.CoreGlobalProps
import org.xpathqs.core.constants.Global
import org.xpathqs.core.reflection.scanPackage
import org.xpathqs.core.selector.block.Block
import org.xpathqs.core.selector.extensions.contains
import org.xpathqs.core.selector.extensions.text
import org.xpathqs.core.util.SelectorFactory.tagSelector
import org.xpathqs.xpathShouldBe

object Page: Block() {
    object SignUp: Block(
        tagSelector("div") contains
                tagSelector("h1").text("Where the world builds software", contains = true)
    ) {
        val title = tagSelector("h1")
    }
}

class UpperCaseTest : AnnotationSpec() {

    @AfterEach
    fun restoreDefaults() {
        Global.update(
            CoreGlobalProps("config/configDefault.yml")
        )
    }

    @Test
    fun normalCase() {
        Global.update(
            CoreGlobalProps("config/config.yml")
        )
        scanPackage(this)
        Page.SignUp.title
            .xpathShouldBe("//DIV[./H1[contains(@text_test, 'Where the world builds software')]]//H1")
    }
}