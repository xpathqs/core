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

package org.xpathqs.core.annotations

/**
 * Adds ability to change Selector's name
 *
 * Require #1 - Selector's name should be set to the [value] for the member
 * @sample [org.xpathqs.core.reflection.annotations.NameAnnotationTest.r1]
 *
 * Require #2 - Selector's name should be set to the [value] for the object
 * @sample [org.xpathqs.core.reflection.annotations.NameAnnotationTest.r2]
 *
 * Require #3 - Selector's name should be set for the member and object
 * @sample [org.xpathqs.core.reflection.annotations.NameAnnotationTest.r3]
 *
 * Require #4 - Selector's name should be set for the inner object
 * @sample [org.xpathqs.core.reflection.annotations.NameAnnotationTest.r4]
 *
 * Require #5 - Selector's name should be set for the member of block class
 * @sample [org.xpathqs.core.reflection.annotations.NameAnnotationTest.r5]
 *
 * Require #6 - Selector's name should be set for the member of block class with annotation
 * @sample [org.xpathqs.core.reflection.annotations.NameAnnotationTest.r6]
 *
 * Require #7 - Selector's name should be set for the member and class annotation should be ignored
 * @sample [org.xpathqs.core.reflection.annotations.NameAnnotationTest.r7]
 */
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class Name(val value: String)
