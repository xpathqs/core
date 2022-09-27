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

package org.xpathqs.core.reflection.annotations

import io.kotest.core.spec.style.AnnotationSpec
import org.xpathqs.core.annotations.Name
import org.xpathqs.core.reflection.scanPackage
import org.xpathqs.core.selector.block.Block
import org.xpathqs.core.util.SelectorFactory.tagSelector
import org.xpathqs.gwt.WHEN

object Page: Block() {
    @Name("Sel1")
    val s1 = tagSelector("div")
}

@Name("P2")
object Page2: Block() {
    val s1 = tagSelector("div")
}

@Name("P3")
object Page3: Block() {
    @Name("Sel1")
    val s1 = tagSelector("div")
}

object Page4: Block() {
    @Name("Sel1")
    object Inn: Block() {
        val s1 = tagSelector("div")
    }
}

class InnerMember: Block() {
    val s1 = tagSelector("div")

    @Name("Sel2")
    val s2 = tagSelector("div")
}

@Name("IM2")
class InnerMember2: Block() {
    val s1 = tagSelector("div")

    @Name("Sel2")
    val s2 = tagSelector("div")
}

object Page5: Block() {
    val inn = InnerMember()

    @Name("Inner2")
    val inn2 = InnerMember()

    val inn3 = InnerMember2()
}

class NameAnnotationTest : AnnotationSpec() {
    init {
        scanPackage(this)
    }

    /**
     * Checks Require #1 of [Name]
     */
    @Test
    fun r1() {
        WHEN {
            Page.s1.name
        }.THEN(
            "Page.Sel1"
        )
    }

    /**
     * Checks Require #2 of [Name]
     */
    @Test
    fun r2() {
        WHEN {
            Page2.s1.name
        }.THEN(
            "P2.s1"
        )
    }

    /**
     * Checks Require #3 of [Name]
     */
    @Test
    fun r3() {
        WHEN {
            Page3.s1.name
        }.THEN(
            "P3.Sel1"
        )
    }

    /**
     * Checks Require #4 of [Name]
     */
    @Test
    fun r4() {
        WHEN {
            Page4.Inn.s1.name
        }.THEN(
            "Page4.Sel1.s1"
        )
    }

    /**
     * Checks Require #5 of [Name]
     */
    @Test
    fun r5() {
        WHEN {
            Page5.inn.s2.name
        }.THEN(
            "Page5.inn.Sel2"
        )
    }

    /**
     * Checks Require #6 of [Name]
     */
    @Test
    fun r6() {
        WHEN {
            Page5.inn2.s2.name
        }.THEN(
            "Page5.Inner2.Sel2"
        )
    }

    /**
     * Checks Require #7 of [Name]
     */
    @Test
    fun r7() {
        WHEN {
            Page5.inn3.s2.name
        }.THEN(
            "Page5.inn3.Sel2"
        )
    }
}