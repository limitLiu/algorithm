package wiki.mdzz.utils.tree

import wiki.mdzz.printer.BinaryTreeInfo
import wiki.mdzz.utils.queue.Queue
import wiki.mdzz.utils.stack.Stack
import kotlin.math.max

abstract class BinaryTree<E> : BinaryTreeInfo {
    protected var size: Int = 0
    protected var root: Node<E>? = null

    fun size(): Int {
        return size
    }

    val isEmpty: Boolean
        get() = size() == 0

    fun clear() {
        root = null
        size = 0
    }

    protected open inner class Node<E>(e: E, var parent: Node<E>?) {
        var element: E = e
        var left: Node<E>? = null
        var right: Node<E>? = null

        fun isLeaf(): Boolean {
            return left == null && right == null
        }

        fun hasTwoChildren(): Boolean {
            return left != null && right != null
        }

        fun isLeftChild(): Boolean {
            return parent != null && this == parent?.left
        }

        fun isRightChild(): Boolean {
            return parent != null && this == parent?.right
        }

        fun sibling(): Node<E>? {
            if (isLeftChild()) {
                return parent?.right
            }
            if (isRightChild()) {
                return parent?.left
            }
            return null
        }
    }

    /*
    fun preorder() {
        preorder(root)
    }

    private fun preorder(node: Node<E>?) {
        if (node == null) return
        println(node.element)
        preorder(node.left)
        preorder(node.right)
    }
    fun preorder(visitor: ((e: E?) -> Boolean)?) {
        if (root == null || visitor == null) return
        val stack = Stack<Node<E>>()
        var node = root
        while (node != null || !stack.isEmpty) {
            while (node != null) {
                if (visitor(node.element)) return
                stack.push(node)
                node = node.left
            }
            if (!stack.isEmpty) {
                node = stack.pop()
                node = node!!.right
            }
        }
    }
     */

    fun preOrder(visitor: ((e: E?) -> Boolean)?) {
        if (root == null || visitor == null) return
        val stack = Stack<Node<E>>()
        stack.push(root)
        while (!stack.isEmpty) {
            val node = stack.pop()
            if (visitor(node!!.element)) return
            if (node.right != null) {
                stack.push(node.right)
            }
            if (node.left != null) {
                stack.push(node.left)
            }
        }
    }

    fun preorder(visitor: ((e: E?) -> Boolean)?) {
        if (root == null || visitor == null) return
        val stack = Stack<Node<E>>()
        var node = root
        while (true) {
            node = if (node != null) {
                if (visitor(node.element)) return
                if (node.right != null) stack.push(node.right)
                node.left
            } else if (!stack.isEmpty) {
                stack.pop()
            } else {
                break
            }
        }
    }

    /*
    fun inorder() {
        inorder(root)
    }

    private fun inorder(node: Node<E>?) {
        if (node == null) return
        inorder(node.left)
        println(node.element)
        inorder(node.right)
    }
    fun inorder(visitor: ((e: E?) -> Boolean)?) {
        if (root == null || visitor == null) return
        val stack = Stack<Node<E>>()
        var node = root
        while (node != null || !stack.isEmpty) {
            while (node != null) {
                stack.push(node)
                node = node.left
            }
            if (!stack.isEmpty) {
                node = stack.pop()
                visitor(node!!.element)
                node = node.right
            }
        }
    }
     */

    fun inorder(visitor: ((e: E?) -> Boolean)?) {
        if (root == null || visitor == null) return
        val stack = Stack<Node<E>>()
        var node = root
        while (true) {
            if (node != null) {
                stack.push(node)
                node = node.left
            } else if (!stack.isEmpty) {
                node = stack.pop()
                if (visitor(node!!.element)) return
                node = node.right
            } else {
                break
            }
        }
    }

    /*
    fun postOrder() {
        postOrder(root)
    }

    private fun postOrder(node: Node<E>?) {
        if (node == null) return
        postOrder(node.left)
        postOrder(node.right)
        println(node.element)
    }
     */
    fun postOrder(visitor: ((e: E?) -> Boolean)?) {
        if (root == null || visitor == null) return
        val stack = Stack<Node<E>>()
        var node = root
        var last: Node<E>? = null
        while (node != null || !stack.isEmpty) {
            while (node != null) {
                stack.push(node)
                node = node.left
            }
            if (!stack.isEmpty) {
                node = stack.peek()
                if (node!!.right != null && last != node.right) {
                    node = node.right
                } else {
                    node = stack.pop()
                    if (visitor(node!!.element)) return
                    last = node
                    node = null
                }
            }
        }
    }

    fun postorder(visitor: ((e: E?) -> Boolean)?) {
        if (root == null || visitor == null) return
        val stack = Stack<Node<E>>()
        stack.push(root)
        var prev: Node<E>? = null
        while (!stack.isEmpty) {
            val top = stack.peek()
            if (top!!.isLeaf() || (prev != null && prev.parent == top)) {
                prev = stack.pop()
                if (visitor(prev!!.element)) return
            } else {
                if (top.right != null) {
                    stack.push(top.right)
                }
                if (top.left != null) {
                    stack.push(top.left)
                }
            }
        }
    }

    fun levelOrder(visitor: ((e: E?) -> Boolean)?) {
        if (root == null || visitor == null) return
        val queue = Queue<Node<E>>()
        queue.offer(root)
        while (!queue.isEmpty) {
            val node = queue.poll()
            if (visitor(node!!.element)) return
            if (node.left != null) {
                queue.offer(node.left)
            }
            if (node.right != null) {
                queue.offer(node.right)
            }
        }
    }

    /*
    fun isComplete(): Boolean {
        if (root == null) return false
        val queue = Queue<Node<E>>()
        queue.offer(root)
        var leaf = false
        while (!queue.isEmpty()) {
            val node = queue.poll()
            if (leaf && !node!!.isLeaf()) return false
            if (node!!.hasTwoChildren()) {
                queue.offer(node.left)
                queue.offer(node.right)
            } else if (node.left == null && node.right != null) {
                return false
            } else {
                leaf = true
                if (node.left != null) {
                    queue.offer(node.left)
                }
            }
        }
        return true
    }
     */
    val isComplete: Boolean
        get() {
            if (root == null) return false
            val queue = Queue<Node<E>>()
            queue.offer(root)
            var leaf = false
            while (!queue.isEmpty) {
                val node = queue.poll()
                if (leaf && !node!!.isLeaf()) return false
                if (node!!.left != null) {
                    queue.offer(node.left)
                } else if (node.right != null) {
                    return false
                }
                if (node.right != null) {
                    queue.offer(node.right)
                } else {
                    leaf = true
                }
            }
            return true
        }

    protected fun predecessor(node: Node<E>?): Node<E>? {
        var tmp: Node<E>? = node ?: return null
        var p = tmp!!.left
        if (p != null) {
            while (p!!.right != null) {
                p = p.right
            }
            return p
        }
        while (tmp!!.parent != null && tmp.parent?.left == node) {
            tmp = tmp.parent
        }
        return tmp.parent
    }

    protected fun successor(node: Node<E>?): Node<E>? {
        var tmp: Node<E>? = node ?: return null
        var p = tmp!!.right
        if (p != null) {
            while (p!!.left != null) {
                p = p.left
            }
            return p
        }
        while (tmp!!.parent != null && tmp.parent?.right == node) {
            tmp = tmp.parent
        }
        return tmp.parent
    }

    /*
    fun height(): Int {
        if (root == null) return 0
        val stack = Stack<Node<E>>()
        var node = root
        var last: Node<E>? = null
        var h = 0
        while (node != null || !stack.isEmpty) {
            while (node != null) {
                stack.push(node)
                node = node.left
            }
            if (!stack.isEmpty) {
                node = stack.peek()
                if (node!!.right != null && last != node.right) {
                    node = node.right
                } else {
                    h = max(h, stack.size())
                    node = stack.pop()
                    last = node
                    node = null
                }
            }
        }
        return h
    }
     */

    /*
    fun height(): Int {
        if (root == null) return 0
        val queue = Queue<Node<E>>()
        queue.offer(root)
        var levelSize = 1
        var h = 0
        while (!queue.isEmpty) {
            val node = queue.poll()
            if (node!!.left != null) {
                queue.offer(node.left)
            }
            if (node.right != null) {
                queue.offer(node.right)
            }
            if (--levelSize == 0) {
                levelSize = queue.size()
                h++
            }
        }
        return h
    }
     */

    val height: Int
        get() = height(root)

    private fun height(node: Node<E>?): Int {
        if (node == null) return 0
        return 1 + max(height(node.left), height(node.right))
    }

    override fun root(): Any {
        return root!!
    }

    override fun right(node: Any?): Any? {
        return (node as Node<*>).right
    }

    override fun left(node: Any?): Any? {
        return (node as Node<*>).left
    }

    override fun string(node: Any?): Any? {
        return node
    }
}
