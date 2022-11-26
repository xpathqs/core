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

package org.xpathqs.core.annotations

import org.xpathqs.core.selector.base.BaseSelector
import org.xpathqs.core.selector.group.GroupSelector
import org.xpathqs.core.selector.xpath.XpathSelector

/**
 * Annotation will remove base xpath from result xpath
 *
 * @require #1 - Annotated selector should ignore base xpath for [BaseSelector]
 * @require #2 - Annotated selector should ignore base xpath for [GroupSelector]
 * @require #3 - Annotated selector should ignore base xpath for [XpathSelector]
 *
 * @test [org.xpathqs.core.reflection.annotations.NoXpathBaseTest]
 */
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class NoXpathBase
