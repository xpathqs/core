/*
 * Copyright (c) 2021 Nikita A. Chegodaev
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

package org.xpathqs.core.reflection.pages

import org.xpathqs.core.selector.Block
import org.xpathqs.core.selector.extensions.get
import org.xpathqs.core.selector.extensions.textNotEmpty
import org.xpathqs.core.selector.selector.Selector
import org.xpathqs.core.util.SelectorFactory
import org.xpathqs.core.util.SelectorFactory.tagSelector

object HtmlTags {
    val DIV: Selector
        get() = tagSelector("div")
}

class AppTable(pos: Int = 1) : Block(
    SelectorFactory.xpathSelector("//div[./div/div/span[text()='Application']]")[pos]
) {
    inner class Row : Block(
        SelectorFactory.xpathSelector("/div[count(.//div/div) > 3]")
    ) {
        open inner class Col(num: Int = 1) : Block(
            SelectorFactory.xpathSelector("/div")[num]
        )

        inner class App : Col(1) {
            val id = HtmlTags.DIV.textNotEmpty()[1]
            val from = HtmlTags.DIV.textNotEmpty()[2]
            val date = HtmlTags.DIV.textNotEmpty()[3]
        }

        val app = App()

        inner class Principal : Col(2) {
            val title = HtmlTags.DIV.textNotEmpty()[1]
            val inn = HtmlTags.DIV.textNotEmpty()[2]
        }

        val principal = Principal()
    }

    val rows = Row()
}

object PageWithInnerClassMembers : Block() {
    val table1 = AppTable(1)
    val table2 = AppTable(2)
}