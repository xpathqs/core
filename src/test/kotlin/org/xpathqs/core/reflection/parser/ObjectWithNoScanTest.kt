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

package org.xpathqs.core.reflection.parser

import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.xpathqs.core.reflection.PageWithBase
import org.xpathqs.core.reflection.PageWithNoScan
import org.xpathqs.core.reflection.SelectorParser
import org.xpathqs.xpathShouldBe


internal class ObjectWithNoScanTest {

    @BeforeEach
    fun before() {
        SelectorParser(PageWithNoScan).parse()
    }

    @Test
    fun checkSelectorName() {
        assertThat(PageWithNoScan.s1.name)
            .isEqualTo("PageWithNoScan.s1")
    }

    @Test
    fun checkSelectorNoScanName() {
        assertThat(PageWithNoScan.s2.name)
            .isEmpty()
    }

    @Test
    fun checkSelectorXpath() {
        PageWithNoScan.s1
            .xpathShouldBe("//base//s1")
    }

    @Test
    fun checkSelectorNoScanXpath() {
        PageWithNoScan.s2
            .xpathShouldBe("//s2")
    }

    @Test
    fun checkSelectorForMemberField() {
        PageWithNoScan.holder1.sel1
            .xpathShouldBe("//base//base//s1")
    }

    @Test
    fun checkSelectorNoScanForMemberField() {
        PageWithNoScan.holder2.sel1
            .xpathShouldBe("//s1")
    }

    @Test
    fun checkSelectorForMemberClsField() {
        PageWithNoScan.holder_ns1.sel1
            .xpathShouldBe("//base//base_2//s1")
    }

    @Test
    fun checkSelectorForMemberClsNoScanField() {
        PageWithNoScan.holder_ns1.sel2
            .xpathShouldBe("//s2")
    }

    @Test
    fun checkSelectorForMemberClsAllNoScanField() {
        PageWithNoScan.holder_ns2.sel1
            .xpathShouldBe("//s1")
    }

    @Test
    fun checkSelectorForMemberObjectField() {
        PageWithNoScan.Holder3.sel1
            .xpathShouldBe("//base//base//s1")
    }

    @Test
    fun checkSelectorForMemberObjectAllNoScanField() {
        PageWithNoScan.Holder4.sel1
            .xpathShouldBe("//s1")
    }

    @Test
    fun checkSelectorForMemberObjectInheritanceAllNoScanField() {
        PageWithNoScan.InnerObject2.s1
            .xpathShouldBe("//s1")
    }
}