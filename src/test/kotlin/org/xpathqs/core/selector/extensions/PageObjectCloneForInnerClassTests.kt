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

package org.xpathqs.core.selector.extensions

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.AnnotationSpec
import org.xpathqs.core.reflection.SelectorParser
import org.xpathqs.core.reflection.pages.PageWithInnerClassMembers
import org.xpathqs.core.selector.block.deepClone
import org.xpathqs.shouldBeCloned

class PageObjectCloneForInnerClassTests : AnnotationSpec() {

    fun init() {
        SelectorParser(PageWithInnerClassMembers).parse()
    }

    @Test
    fun checkClone() {
        val cloned = PageWithInnerClassMembers.table1.rows.deepClone()

        assertSoftly {
            cloned.shouldBeCloned()
            cloned.app.shouldBeCloned()
            cloned.principal.shouldBeCloned()
            cloned.app.id.shouldBeCloned()
        }
    }
}