package wiki.mdzz.utils.tree

import wiki.mdzz.utils.map.HashMap

class Trie<V> {
    private var size = 0
    private var root: Node<V>? = null

    fun size(): Int {
        return size
    }

    val isEmpty: Boolean
        get() = size() == 0

    fun clear() {
        size = 0
        root = null
    }

    fun add(key: String?, value: V?): V? {
        check(key)
        if (root == null) {
            root = Node(null)
        }
        var node = root
        for (c in key!!) {
            var child = node!!.children?.get(c)
            if (child == null) {
                child = Node(node)
                child.character = c
                node.children = if (node.hasChildren()) {
                    node.children
                } else {
                    HashMap()
                }
                node.children!!.put(c, child)
            }
            node = child
        }
        if (node!!.isWord) {
            val old = node.value
            node.value = old
            return old
        }
        node.isWord = true
        node.value = value
        ++size
        return null
    }

    operator fun get(key: String?): V? {
        val node = getNode(key)
        return if (node in this) {
            node!!.value
        } else {
            null
        }
    }

    fun remove(key: String): V? {
        var node = getNode(key)
        if (node !in this) return null
        --size
        val old = node!!.value
        if (!(node.isNullOrEmpty())) {
            node.isWord = false
            node.value = null
            return old
        }

        var parent: Node<V>?
        while (node!!.parent.also { parent = it } != null) {
            parent!!.children!!.remove(node.character)
            if (parent!!.isWord || !parent!!.children!!.isEmpty) break
            node = parent
        }
        return old
    }

    fun startsWith(prefix: String?): Boolean {
        return getNode(prefix) != null
    }

    operator fun contains(key: String?): Boolean {
        val node = getNode(key)
        return node in this
    }

    private operator fun contains(node: Node<V>?): Boolean {
        return node != null && node.isWord
    }

    private fun getNode(key: String?): Node<V>? {
        check(key)
        var node: Node<V>? = root
        for (c in key!!) {
            if (node == null || node.isNullOrEmpty()) return null
            node = node.children!![c]
        }
        return node
    }

    private fun check(key: String?) {
        if (key.isNullOrEmpty()) {
            throw IllegalArgumentException("Key must not be empty.")
        }
    }

    inner class Node<V>(var parent: Node<V>?) {
        var children: HashMap<Char, Node<V>>? = null
        var value: V? = null
        var isWord = false
        var character: Char? = null

        fun isNullOrEmpty(): Boolean {
            return children == null || children!!.isEmpty
        }

        fun hasChildren(): Boolean {
            return children != null
        }
    }
}
