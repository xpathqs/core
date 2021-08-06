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

package org.xpathqs.core.selector.selector

/**
 * Selects all ancestors (parent, grandparent, etc.) of the current node
 */
fun <T : Selector> T.ancestor() = prefix("ancestor::")

/**
 * Selects all ancestors (parent, grandparent, etc.) of the current node and the current node itself
 */
fun <T : Selector> T.ancestorOrSelf() = prefix("ancestor-or-self::")

/**
 * Selects all children of the current node
 */
fun <T : Selector> T.child() = prefix("child::")

/**
 * Selects all descendants (children, grandchildren, etc.) of the current node
 */
fun <T : Selector> T.descendant() = prefix("descendant::")

/**
 * Selects all descendants (children, grandchildren, etc.) of the current node and the current node itself
 */
fun <T : Selector> T.descendantOrSelf() = prefix("descendant-or-self::")

/**
 * Selects everything in the document after the closing tag of the current node
 */
fun <T : Selector> T.following() = prefix("following::")

/**
 * Selects all siblings after the current node
 */
fun <T : Selector> T.followingSibling() = prefix("following-sibling::")

/**
 * 	Selects all namespace nodes of the current node
 */
fun <T : Selector> T.namespace() = prefix("namespace::")

/**
 * Selects the parent of the current node
 */
fun <T : Selector> T.parent() = prefix("parent::")

/**
 * Selects all nodes that appear before the current node in the document,
 * except ancestors, attribute nodes and namespace nodes
 */
fun <T : Selector> T.preceding() = prefix("preceding::")

/**
 * Selects all siblings before the current node
 */
fun <T : Selector> T.precedingSibling() = prefix("preceding-sibling::")

/**
 * 	Selects the current node
 */
fun <T : Selector> T.self() = prefix("self::")