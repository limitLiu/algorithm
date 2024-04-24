package net.wu_chinese.utils.map

import java.util.Objects
import net.wu_chinese.utils.queue.Queue

open class HashMap<K, V> : Map<K, V> {
    companion object {
        const val RED = false
        const val BLACK = true
        const val DEFAULT_CAPACITY = 1 shl 4
        const val DEFAULT_LOAD_FACTOR = 0.75f
    }

    private var table: Array<Node<K, V>?>

    private var size: Int = 0

    override val isEmpty: Boolean
        get() = size() == 0

    override fun size(): Int {
        return size
    }

    override fun clear() {
        if (isEmpty) return
        size = 0
        table.fill(null)
    }

    override fun put(key: K, value: V): V? {
        resize()
        val index = getIndex(key)
        var root = table[index]
        if (root == null) {
            root = createNode(key, value, null)
            table[index] = root
            ++size
            fixAfterPut(root)
            return null
        }
        var parent: Node<K, V>?
        var node = root
        var cmp = 0
        val h1 = getHash(key)
        var result: Node<K, V>? = null
        var searched = false
        do {
            parent = node
            val k2: K = node!!.key
            val h2 = node.hash
            if (h1 > h2) {
                cmp = 1
            } else if (h1 < h2) {
                cmp = -1
            } else if (Objects.equals(key, k2)) {
                cmp = 0
            } else if (k2 != null && key is Comparable<*> && key.javaClass == k2.javaClass &&
                (key as Comparable<K>).compareTo(k2).let { cmp = it; cmp != 0 }
            ) {
                // Nothing to do
            } else if (searched) {
                cmp = System.identityHashCode(key) - System.identityHashCode(k2)
            } else {
                if (node.left != null && getNode(node.left, key).let {
                        result = it; result != null
                    }) {
                    node = result
                    cmp = 0
                } else {
                    searched = true
                    cmp = System.identityHashCode(key) - System.identityHashCode(k2)
                }
            }
            if (cmp > 0) {
                node = node!!.right
            } else if (cmp < 0) {
                node = node!!.left
            } else {
                val oldValue: V = node!!.value
                node.key = key
                node.value = value
                node.hash = h1
                return oldValue
            }
        } while (node != null)

        val newNode = createNode(key, value, parent)
        if (cmp > 0) {
            parent!!.right = newNode
        } else {
            parent!!.left = newNode
        }
        ++size
        fixAfterPut(newNode)
        return null
    }

    private fun resize() {
        if ((size.toFloat() / table.size.toFloat()) <= DEFAULT_LOAD_FACTOR) {
            return
        }

        val oldTable = table
        table = arrayOfNulls(oldTable.size shl 1)
        val queue = Queue<Node<K, V>>()
        for (kvNode in oldTable) {
            if (kvNode == null) continue
            queue.offer(kvNode)
            while (!queue.isEmpty) {
                val node = queue.poll()
                assert(node != null)
                if (node!!.left != null) {
                    queue.offer(node.left)
                }
                if (node.right != null) {
                    queue.offer(node.right)
                }
                moveNode(node)
            }
        }
    }

    private fun moveNode(newNode: Node<K, V>) {
        newNode.parent = null
        newNode.left = null
        newNode.right = null
        newNode.color = RED
        val index = getIndex(newNode)
        var root = table[index]
        if (root == null) {
            root = newNode
            table[index] = root
            fixAfterPut(root)
            return
        }

        var parent: Node<K, V>?
        var node = root
        var cmp = 0
        val k1: K = newNode.key
        val h1 = newNode.hash
        do {
            parent = node
            val k2: K = node!!.key
            val h2 = node.hash
            if (h1 > h2) {
                cmp = 1
            } else if (h1 < h2) {
                cmp = -1
            } else if (
                k2 != null &&
                k1 is Comparable<*> &&
                k1.javaClass == k2.javaClass
                && (k1 as Comparable<K>).compareTo(k2).let { cmp = it; cmp != 0 }
            ) {
                cmp = System.identityHashCode(k1) - System.identityHashCode(k2)
            }
            if (cmp > 0) {
                node = node.right
            } else if (cmp < 0) {
                node = node.left
            }
        } while (node != null)

        newNode.parent = parent
        if (cmp > 0) {
            parent?.right = newNode
        } else {
            parent?.left = newNode
        }
        fixAfterPut(newNode)
    }

    private fun fixAfterPut(node: Node<K, V>?) {
        val parent = node?.parent
        if (parent == null) {
            setBlack(node)
            return
        }
        if (isBlack(parent)) return

        val uncle = parent.sibling()
        val grand = setRed(parent.parent)
        if (isRed(uncle)) {
            setBlack(parent)
            setBlack(uncle)
            fixAfterPut(grand)
            return
        }
        if (parent.isLeftChild()) {
            if (node.isLeftChild()) {
                setBlack(parent)
            } else {
                setBlack(node)
                rotateLeft(parent)
            }
            rotateRight(grand)
        } else {
            if (node.isLeftChild()) {
                setBlack(node)
                rotateRight(parent)
            } else {
                setBlack(parent)
            }
            rotateLeft(grand)
        }
    }

    override fun set(key: K, value: V) {
        put(key, value)
    }

    override fun get(key: K): V? {
        return getNode(key)?.value
    }

    override fun remove(key: K?): V? {
        return remove(getNode(key))
    }

    protected open fun afterRemove(willRemove: Node<K, V>, shouldRemove: Node<K, V>) {
    }

    protected open fun createNode(key: K, value: V, parent: Node<K, V>?): Node<K, V> {
        return Node(key, value, parent)
    }

    protected open fun remove(valNode: Node<K, V>?): V? {
        if (valNode == null) return null
        var node = valNode
        --size
        val oldValue = node.value
        if (node.hasTwoChildren()) {
            val s = successor(valNode)
            if (s != null) {
                node.key = s.key
                node.value = s.value
                node.hash = s.hash
                node = s
            }
        }
        val replacement = node.left ?: node.right

        val index = getIndex(node)
        if (replacement != null) {
            replacement.parent = node.parent
            if (node.parent == null) {
                table[index] = replacement
            } else if (node == node.parent!!.left) {
                node.parent!!.left = replacement
            } else {
                node.parent!!.right = replacement
            }
            fixAfterRemove(replacement)
        } else if (node.parent == null) {
            table[index] = null
        } else {
            if (node == node.parent?.left) {
                node.parent?.left = null
            } else {
                node.parent?.right = null
            }
            fixAfterRemove(node)
        }

        afterRemove(valNode, node)
        return oldValue
    }

    private fun fixAfterRemove(node: Node<K, V>?) {
        if (isRed(node)) {
            setBlack(node)
            return
        }
        val parent: Node<K, V> = node?.parent ?: return
        val left = parent.left == null || node.isLeftChild()
        var sibling = if (left) {
            parent.right
        } else {
            parent.left
        }
        if (left) {
            if (isRed(sibling)) {
                setBlack(sibling)
                setRed(parent)
                rotateLeft(parent)
                sibling = parent.right
            }
            if (isBlack(sibling?.left) && isBlack(sibling?.right)) {
                val parentBlack = isBlack(parent)
                setBlack(parent)
                setRed(sibling)
                if (parentBlack) {
                    fixAfterRemove(parent)
                }
            } else {
                if (isBlack(sibling?.right)) {
                    rotateRight(sibling)
                    sibling = parent.right
                }
                setColor(sibling, colorOf(parent))
                setBlack(sibling?.right)
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
            if (isBlack(sibling?.left) && isBlack(sibling?.right)) {
                val parentBlack = isBlack(parent)
                setBlack(parent)
                setRed(sibling)
                if (parentBlack) {
                    fixAfterRemove(parent)
                }
            } else {
                if (isBlack(sibling?.left)) {
                    rotateLeft(sibling)
                    sibling = parent.left
                }
                setColor(sibling, colorOf(parent))
                setBlack(sibling?.left)
                setBlack(parent)
                rotateRight(parent)
            }
        }
    }

    private fun rotateLeft(grand: Node<K, V>?) {
        val parent = grand?.right
        val child = parent?.left
        if (grand != null) {
            grand.right = child
        }
        if (parent != null) {
            parent.left = grand
        }
        afterRotate(grand, parent, child)
    }

    private fun rotateRight(grand: Node<K, V>?) {
        val parent = grand?.left
        val child = parent?.right
        if (grand != null) {
            grand.left = child
        }
        if (parent != null) {
            parent.right = grand
        }
        afterRotate(grand, parent, child)
    }

    private fun afterRotate(grand: Node<K, V>?, parent: Node<K, V>?, child: Node<K, V>?) {
        parent?.parent = grand!!.parent
        if (grand.isLeftChild()) {
            grand.parent?.left = grand
        } else if (grand.isRightChild()) {
            grand.parent?.right = parent
        } else {
            table[getIndex(grand)] = parent
        }
        if (child != null) {
            child.parent = grand
        }
        grand.parent = parent
    }

    private fun isRed(node: Node<K, V>?): Boolean {
        return colorOf(node) == RED
    }

    private fun isBlack(node: Node<K, V>?): Boolean {
        return colorOf(node) == BLACK
    }

    private fun colorOf(node: Node<K, V>?): Boolean {
        return node?.color ?: BLACK
    }

    private fun setBlack(node: Node<K, V>?): Node<K, V>? {
        return setColor(node, BLACK)
    }

    private fun setRed(node: Node<K, V>?): Node<K, V>? {
        return setColor(node, RED)
    }

    private fun setColor(node: Node<K, V>?, color: Boolean): Node<K, V>? {
        if (node == null) return null
        node.color = color
        return node
    }

    private fun successor(current: Node<K, V>?): Node<K, V>? {
        if (current == null) return null
        var node = current
        var p = node.right
        if (p != null) {
            while (p?.left != null) {
                p = p.left
            }
            return p
        }
        while (node?.parent != null && node == node.parent?.right) {
            node = node.parent
        }
        return node?.parent
    }

    override fun contains(key: K?): Boolean {
        return getNode(key) != null
    }

    override fun containsValue(value: V): Boolean {
        if (isEmpty) return false
        val queue = Queue<Node<K, V>>()
        for (kvNode in table) {
            if (kvNode == null) continue
            queue.offer(kvNode)
            while (!queue.isEmpty) {
                val node = queue.poll()
                assert(node != null)
                if (Objects.equals(value, node!!.value)) return true
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
        val queue = Queue<Node<K, V>>()
        for (kvNode in table) {
            if (kvNode == null) continue
            queue.offer(kvNode)
            while (!queue.isEmpty) {
                val node = queue.poll()
                assert(visitor != null)
                assert(node != null)
                if (visitor!!.invoke(node!!.key, node.value)) return
                if (node.left != null) {
                    queue.offer(node.left)
                }
                if (node.right != null) {
                    queue.offer(node.right)
                }
            }
        }
    }

    private fun getNode(key: K?): Node<K, V>? {
        val root = table[getIndex(key)]
        return if (root == null) {
            null
        } else {
            getNode(root, key)
        }
    }

    private fun getIndex(key: K?): Int {
        return getHash(key) and (table.size - 1)
    }

    private fun getIndex(node: Node<K, V>): Int {
        return node.hash and (table.size - 1)
    }

    private fun getHash(key: K?): Int {
        if (key == null) return 0
        val temp = key.hashCode()
        return temp xor (temp ushr 16)
    }

    private fun getNode(valNode: Node<K, V>?, k1: K?): Node<K, V>? {
        var node = valNode
        val h1 = getHash(k1)
        var result: Node<K, V>? = null
        var cmp = 0
        while (node != null) {
            val k2 = node.key
            val h2 = node.hash
            if (h1 > h2) {
                node = node.right
            } else if (h1 < h2) {
                node = node.left
            } else if (Objects.equals(k1, k2)) {
                return node
            } else if (k2 != null
                && k1 is Comparable<*>
                && k1.javaClass == k2.javaClass
                && (k1 as Comparable<K>).compareTo(k2).let { cmp = it; cmp != 0 }
            ) {
                node = if (cmp > 0) {
                    node.right
                } else {
                    node.left
                }
            } else if (node.right != null && getNode(node.right, k1).let {
                    result = it; result != null
                }) {
                return result
            } else {
                node = node.left
            }
        }
        return null
    }

    init {
        table = arrayOfNulls<Node<K, V>>(DEFAULT_CAPACITY)
    }


    open inner class Node<K, V>(var key: K, var value: V, var parent: Node<K, V>?) {
        var color: Boolean = TreeMap.RED
        var left: Node<K, V>? = null
        var right: Node<K, V>? = null
        var hash: Int = 0

        init {
            val hash = key?.hashCode() ?: 0
            this.hash = hash xor (hash ushr 16)
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
}
