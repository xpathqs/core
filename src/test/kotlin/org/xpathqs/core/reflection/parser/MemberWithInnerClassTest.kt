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

package org.xpathqs.core.reflection.parser

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.AnnotationSpec
import org.xpathqs.core.reflection.SelectorParser
import org.xpathqs.core.reflection.pages.AppTable
import org.xpathqs.core.reflection.pages.PageWithInnerClassMembers
import org.xpathqs.shouldBeFreeze

class MemberWithInnerClassTest : AnnotationSpec() {

    @Test
    fun parseMember() {
        val obj = AppTable(1)
        SelectorParser(obj).parse()
        checkState(obj)
    }

    @Test
    fun parseObject() {
        SelectorParser(PageWithInnerClassMembers).parse()
        checkState(PageWithInnerClassMembers.table1)
        checkState(PageWithInnerClassMembers.table2)
    }

    private fun checkState(obj: AppTable) {
        assertSoftly {
            obj.shouldBeFreeze()
            obj.rows.shouldBeFreeze()
            obj.rows.app.shouldBeFreeze()
            obj.rows.principal.shouldBeFreeze()
            obj.rows.app.date.shouldBeFreeze()
            obj.rows.app.from.shouldBeFreeze()
            obj.rows.app.id.shouldBeFreeze()
        }
    }
}