package ltd.rust_lang.utils.map

import ltd.rust_lang.utils.queue.Queue
import ltd.rust_lang.utils.stack.Stack

class TreeMap<K, V>(private val comparator: Comparator<K>? = null) : Map<K, V> {
    companion object {
        const val RED = false
        const val BLACK = true
    }

    inner class Node<K, V>(var key: K, var value: V, var parent: Node<K, V>?) {
        var color: Boolean = RED
        var left: Node<K, V>? = null
        var right: Node<K, V>? = null

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

        fun sibling(): Node<K, V>? {
            if (isLeftChild()) {
                return parent?.right
            }
            if (isRightChild()) {
                return parent?.left
            }
            return null
        }
    }

    private fun getNode(key: K?): Node<K, V>? {
        var tmp = root
        while (tmp != null) {
            val cmp = compare(key, tmp.key)
            tmp = when {
                cmp > 0 -> tmp.right
                cmp < 0 -> tmp.left
                else -> return tmp
            }
        }
        return null
    }

    private fun compare(k1: K?, k2: K): Int {
        if (comparator != null) {
            return comparator.compare(k1, k2)
        }
        return (k1 as Comparable<K>).compareTo(k2)
    }

    private fun keyNotNullCheck(k: K?) {
        if (k == null) {
            throw IllegalArgumentException("key must not be null")
        }
    }

    private fun setColor(color: Boolean, node: Node<K, V>?): Node<K, V>? {
        if (node != null) {
            node.color = color
        }
        return node
    }

    private fun setRed(node: Node<K, V>?): Node<K, V>? {
        return setColor(RED, node)
    }

    private fun setBlack(node: Node<K, V>?): Node<K, V>? {
        return setColor(BLACK, node)
    }

    private fun colorOf(node: Node<K, V>?): Boolean {
        return node?.color ?: BLACK
    }

    private fun isBlack(node: Node<K, V>?): Boolean {
        return colorOf(node) == BLACK
    }

    private fun isRed(node: Node<K, V>?): Boolean {
        return colorOf(node) == RED
    }

    private fun afterPut(node: Node<K, V>?) {
        val parent = node?.parent
        if (parent == null) {
            setBlack(node)
            return
        }

        if (isBlack(parent)) return

        val uncle = parent.sibling()
        val grandpa = setRed(parent.parent)
        if (isRed(uncle)) {
            setBlack(parent)
            setBlack(uncle)
            afterPut(grandpa)
            return
        }
        when (Pair(parent.isLeftChild(), node.isLeftChild())) {
            Pair(first = true, second = true) -> {
                // LL
                setBlack(parent)
                rotateRight(grandpa)
            }
            Pair(first = true, second = false) -> {
                // LR
                setBlack(node)
                rotateLeft(parent)
                rotateRight(grandpa)
            }
            Pair(first = false, second = true) -> {
                // RL
                setBlack(node)
                rotateRight(parent)
                rotateLeft(grandpa)
            }
            Pair(first = false, second = false) -> {
                // RR
                setBlack(parent)
                rotateLeft(grandpa)
            }
        }
    }

    private fun rotateLeft(grandpa: Node<K, V>?) {
        val parent = grandpa!!.right
        val child = parent!!.left
        grandpa.right = child
        parent.left = grandpa

        afterRotate(grandpa, parent, child)
    }

    private fun afterRotate(grandpa: Node<K, V>, parent: Node<K, V>, child: Node<K, V>?) {
        parent.parent = grandpa.parent
        when {
            grandpa.isLeftChild() -> {
                grandpa.parent!!.left = parent
            }
            grandpa.isRightChild() -> {
                grandpa.parent!!.right = parent
            }
            else -> {
                root = parent
            }
        }
        if (child != null) {
            child.parent = grandpa
        }
        grandpa.parent = parent
    }

    private fun rotateRight(grandpa: Node<K, V>?) {
        val parent = grandpa!!.left
        val child = parent!!.right
        grandpa.left = child
        parent.right = grandpa

        afterRotate(grandpa, parent, child)
    }

    private var size: Int = 0
    private var root: Node<K, V>? = null

    override fun size(): Int {
        return size
    }

    override val isEmpty: Boolean
        get() = size() == 0

    override fun clear() {
        root = null
        size = 0
    }

    override operator fun set(key: K, value: V) {
        put(key, value)
    }

    override fun put(key: K, value: V): V? {
        keyNotNullCheck(key)
        if (root == null) {
            root = Node(key, value, null)
            afterPut(root!!)
            size++
            return null
        } else {
            var parent: Node<K, V>?
            var node = root
            var cmp: Int
            do {
                parent = node
                cmp = compare(key, node!!.key)
                node = when {
                    cmp > 0 -> {
                        node.right
                    }
                    cmp < 0 -> {
                        node.left
                    }
                    else -> {
                        val old = node.value
                        node.key = key
                        node.value = value
                        return old
                    }
                }
            } while (node != null)

            val newNode = Node(key, value, parent)
            if (cmp > 0) {
                parent!!.right = newNode
            } else if (cmp < 0) {
                parent!!.left = newNode
            }
            size++
            afterPut(newNode)
            return null
        }
    }

    override operator fun get(key: K): V? {
        return getNode(key)?.value
    }

    private fun afterRemove(tmp: Node<K, V>?) {
        if (isRed(tmp)) {
            setBlack(tmp)
            return
        }
        val parent = tmp!!.parent ?: return
        val isLeft = parent.left == null || tmp.isLeftChild()
        var sibling = if (isLeft) {
            parent.right
        } else {
            parent.left
        }
        if (isLeft) {
            if (isRed(sibling)) {
                setBlack(sibling)
                setRed(parent)
                rotateLeft(parent)
                sibling = parent.right
            }
            if (isBlack(sibling!!.left) && isBlack(sibling.right)) {
                val isParentBlack = isBlack(parent)
                setBlack(parent)
                setRed(sibling)
                if (isParentBlack) {
                    afterRemove(parent)
                }
            } else {
                if (isBlack(sibling.right)) {
                    rotateRight(sibling)
                    sibling = parent.right
                }
                setColor(colorOf(parent), sibling)
                setBlack(sibling!!.right)
                setBlack(parent)
                rotateLeft(parent)
            }
        } else {
            if (isRed(sibling)) {
                setBlack(sibling)
                setRed(parent)
                rotateRight(parent)
                sibling = parent.left
            }
            if (isBlack(sibling!!.left) && isBlack(sibling.right)) {
                val isParentBlack = isBlack(parent)
                setBlack(parent)
                setRed(sibling)
                if (isParentBlack) {
                    afterRemove(parent)
                }
            } else {
                if (isBlack(sibling.left)) {
                    rotateLeft(sibling)
                    sibling = parent.left
                }
                setColor(colorOf(parent), sibling)
                setBlack(sibling!!.left)
                setBlack(parent)
                rotateRight(parent)
            }
        }
    }

    override fun remove(key: K?): V? {
        return remove(getNode(key))
    }

    private fun successor(node: Node<K, V>?): Node<K, V>? {
        var tmp: Node<K, V>? = node ?: return null
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

    private fun remove(node: Node<K, V>?): V? {
        var current: Node<K, V>? = node ?: return null
        val old = current!!.value
        // 子树(度) 为 2
        if (current.hasTwoChildren()) {
            val s = successor(node)
            current.key = s!!.key
            current.value = s.value
            current = s
        }
        val replacement = if (current.left != null) {
            current.left
        } else {
            current.right
        }
        when {
            replacement != null -> {
                replacement.parent = current.parent
                when {
                    current.parent == null -> {
                        root = replacement
                    }
                    current == current.parent!!.left -> {
                        current.parent!!.left = replacement
                    }
                    else -> {
                        current.parent!!.right = replacement
                    }
                }
                afterRemove(replacement)
            }
            current.parent == null -> { // 根节点
                root = null
            }
            else -> { // 不为根节点的叶子
                if (current == current.parent!!.right) {
                    current.parent!!.right = null
                } else {
                    current.parent!!.left = null
                }
                afterRemove(current)
            }
        }
        size--
        return old
    }

    override operator fun contains(key: K?): Boolean {
        return getNode(key) != null
    }

    override fun containsValue(value: V): Boolean {
        if (root != null) {
            val queue = Queue<Node<K, V>>()
            queue.offer(root)
            while (!queue.isEmpty) {
                val node = queue.poll()
                if (node!!.value == value) {
                    return true
                }
                if (node.left != null) {
                    queue.offer(node.left)
                }
                if (node.right != null) {
                    queue.offer(node.right)
                }
            }
        }
        return false
    }

    override fun traversal(visitor: ((k: K?, v: V?) -> Boolean)?) {
        if (root == null || visitor == null) return
        val stack = Stack<Node<K, V>>()
        var node = root
        while (node != null || !stack.isEmpty) {
            while (node != null) {
                stack.push(node)
                node = node.left
            }
            if (!stack.isEmpty) {
                node = stack.pop()
                if (visitor(node!!.key, node.value)) break
                node = node.right
            }
        }
    }
}
