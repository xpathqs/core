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

package org.xpathqs.core.reflection

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.xpathqs.core.reflection.pages.PageWithInnerClassMembers
import org.xpathqs.core.selector.extensions.core.get
import org.xpathqs.xpathShouldBe

class PageWithInnerClassMembersTest {

    @Test
    fun testXpath() {
        PageWithInnerClassMembers.table1.rows.app
            .xpathShouldBe("//div[./div/div/span[text()='Application']][position()=1]/div[count(.//div/div) > 3]/div[position()=1]")
    }

    @Test
    fun testXpathWithPosition() {
        PageWithInnerClassMembers.table1.rows[2].app
            .xpathShouldBe("//div[./div/div/span[text()='Application']][position()=1]/div[count(.//div/div) > 3][position()=2]/div[position()=1]")
    }

    @Test
    fun fieldPropertyForAllMembers() {
        PageWithInnerClassMembers.table1
    }

    @Test
    fun test2() {

        PageWithInnerClassMembers.table1.rows[2].app
            .xpathShouldBe("//div[./div/div/span[text()='Application']][position()=1]/div[count(.//div/div) > 3][position()=2]/div[position()=1]")

        PageWithInnerClassMembers.table1.rows
            .xpathShouldBe("//div[./div/div/span[text()='Application']][position()=1]/div[count(.//div/div) > 3]")
    }

    companion object {
        @BeforeAll
        @JvmStatic
        fun init() {
            SelectorParser(PageWithInnerClassMembers).parse()
        }
    }
}