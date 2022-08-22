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

package org.xpathqs.core.selector.base

import org.xpathqs.core.selector.extensions.parents
import kotlin.reflect.KClass


/**
 * returns true if provided annotation is present
 * in the selector's annotation list
 */
fun BaseSelector.hasAnnotation(annotation: KClass<*>)
        = annotations.find {
    it.annotationClass == annotation
} != null

/**
 * returns true if provided annotation is present
 * into base selector's annotation list
 *
 * Require #1 - when parent [org.xpathqs.core.selector.block.Block] has provided annotation - return true
 * @sample [org.xpathqs.core.selector.base.BaseSelectorAnnotationsTest.r1_hasParentAnnotation]
 *
 * Require #2 - when parent [org.xpathqs.core.selector.block.Block] doesn't has provided annotation - return false
 * @sample [org.xpathqs.core.selector.base.BaseSelectorAnnotationsTest.r2_hasParentAnnotation]
 */
fun BaseSelector.hasParentAnnotation(annotation: KClass<*>)
        = (base as? BaseSelector)?.hasAnnotation(annotation) == true

/**
 * returns true if provided annotation is present
 * into base selector's annotation list and in the any of it's parent
 *
 * Require #1 - when parent [org.xpathqs.core.selector.block.Block] has provided annotation - return true
 * @sample [org.xpathqs.core.selector.base.BaseSelectorAnnotationsTest.r1_hasAnyParentAnnotation]
 *
 * Require #2 - when parent [org.xpathqs.core.selector.block.Block] doesn't has provided annotation - return false
 * @sample [org.xpathqs.core.selector.base.BaseSelectorAnnotationsTest.r2_hasParentAnnotation]
 */
fun BaseSelector.hasAnyParentAnnotation(annotation: KClass<*>)
    = this.parents.find { it.hasAnnotation(annotation) } != null

/**
 * @return provided annotation object
 *
 * Require #1 - When selector has provided annotation object - it should return
 * @sample [org.xpathqs.core.selector.base.BaseSelectorAnnotationsTest.r1_findAnnotation]
 *
 * Require #2 - When selector doesn't have provided annotation object - null should be return
 * @sample [org.xpathqs.core.selector.base.BaseSelectorAnnotationsTest.r2_findAnnotation]
 */
inline fun<reified T> BaseSelector.findAnnotation(): T? {
    return annotations.find {
        it.annotationClass.java == T::class.java
    } as? T
}

/**
 * @return provided annotation object of parent [org.xpathqs.core.selector.block.Block]
 *
 * Require #1 - When selector's parent has provided annotation object - it should return
 * @sample [org.xpathqs.core.selector.base.BaseSelectorAnnotationsTest.r1_findParentAnnotation]
 *
 * Require #2 - When selector's parent doesn't have provided annotation object - null should be return
 * @sample [org.xpathqs.core.selector.base.BaseSelectorAnnotationsTest.r2_findParentAnnotation]
 */
inline fun<reified T> BaseSelector.findParentAnnotation(): T? {
    return (base as? BaseSelector)?.findAnnotation()
}

/**
 * @return provided annotation object of any of selector's parent
 *
 * Require #1 - When selector's parent has provided annotation object - it should return
 * @sample [org.xpathqs.core.selector.base.BaseSelectorAnnotationsTest.r1_findParentAnnotation]
 *
 * Require #2 - When selector's parent doesn't have provided annotation object - null should be return
 * @sample [org.xpathqs.core.selector.base.BaseSelectorAnnotationsTest.r2_findParentAnnotation]
 */
inline fun<reified T> BaseSelector.findAnyParentAnnotation(): T? {
    return this.parents.find {
        it.findAnnotation<T>() != null
    }?.findAnnotation()
}

/**
 * Return Selector's parent with specified annotation
 *
 * Require #1 - When selector or any of it's parent has a provided annotation, it should be returned
 * @sample [org.xpathqs.core.selector.base.BaseSelectorAnnotationsTest.r1_findParentWithAnnotation]
 *
 * Require #2 - When selector doesn't have provided annotation object - null should be returned
 * @sample [org.xpathqs.core.selector.base.BaseSelectorAnnotationsTest.r2_findParentWithAnnotation]
 */
fun BaseSelector.findParentWithAnnotation(ann: KClass<*>): BaseSelector? {
    return this.parents.find {
        it.hasAnnotation(ann)
    }
}
