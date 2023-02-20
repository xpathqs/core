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

package org.xpathqs.core.reflection.annotations

import org.xpathqs.core.annotations.Name
import org.xpathqs.core.reflection.annotations.inner.InnerPackageCls
import org.xpathqs.core.selector.block.Block
import org.xpathqs.core.util.SelectorFactory


object Page: Block() {
    @Name("Sel1")
    val s1 = SelectorFactory.tagSelector("div")
}

@Name("P2")
object Page2: Block() {
    val s1 = SelectorFactory.tagSelector("div")
}

@Name("P3")
object Page3: Block() {
    @Name("Sel1")
    val s1 = SelectorFactory.tagSelector("div")
}

object Page4: Block() {
    @Name("Sel1")
    object Inn: Block() {
        val s1 = SelectorFactory.tagSelector("div")
    }
}


class InnerMember: Block() {
    val s1 = SelectorFactory.tagSelector("div")

    @Name("Sel2")
    val s2 = SelectorFactory.tagSelector("div")
}

@Name("IM2")
open class InnerMember2: Block() {
    val s1 = SelectorFactory.tagSelector("div")

    @Name("Sel2")
    val s2 = SelectorFactory.tagSelector("div")
}

object Page5: Block() {
    val inn = InnerMember()

    @Name("Inner2")
    val inn2 = InnerMember()

    val inn3 = InnerMember2()
}

object Page6 : InnerMember2() {
    val ss1 = SelectorFactory.tagSelector("div")
}

object Page7 : InnerPackageCls() {
    val ss1 = SelectorFactory.tagSelector("div")
    val ss2 = InnerPackageCls()
}