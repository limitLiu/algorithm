package net.wu_chinese.utils.list

class LinkedList<E> : DefaultList<E>() {
    private var first: Node<E>? = null
    private var last: Node<E>? = null

    inner class Node<E>(var element: E?, var prev: Node<E>?, var next: Node<E>?) {
        override fun toString(): String {
            val sb = StringBuilder()
            if (prev != null) {
                sb.append(prev!!.element)
            } else {
                sb.append("null")
            }
            sb.append("_").append(element).append("_")
            if (next != null) {
                sb.append(next!!.element)
            } else {
                sb.append("null")
            }
            return sb.toString()
        }
    }

    override fun add(index: Int, element: E?): Boolean {
        rangeCheckForAdd(index)
        // size == 0
        // index == 0
        if (index == size) { // 往最后面添加元素
            val old = last
            last = Node(element, old, null)
            if (old == null) { // 这是链表添加的第一个元素
                first = last
            } else {
                old.next = last
            }
        } else {
            val next = getNode(index)
            val prev = next?.prev
            val node = Node(element, prev, next)
            next!!.prev = node
            if (prev == null) { // index == 0
                first = node
            } else {
                prev.next = node
            }
        }
        size++
        return true
    }

    override fun remove(index: Int): E? {
        rangeCheck(index)
        val node = getNode(index)
        val prev = node?.prev
        val next = node?.next
        if (prev == null) { // index == 0
            first = next
        } else {
            prev.next = next
        }
        if (next == null) { // index == size - 1
            last = prev
        } else {
            next.prev = prev
        }
        size--
        return node?.element
    }

    override operator fun set(index: Int, element: E?): E? {
        val node = getNode(index)
        val old = node?.element
        if (element != null) {
            node?.element = element
        }
        return old
    }

    override fun get(index: Int): E? {
        return getNode(index)!!.element
    }

    override fun clear() {
        size = 0
        first = null
        last = null
    }

    override operator fun contains(element: E?): Boolean {
        return indexOf(element) != ELEMENT_NOT_FOUND
    }

    override fun indexOf(element: E?): Int {
        var node = first
        for (i in 0 until size()) {
            if (element == node?.element) return i
            node = node?.next
        }
        return ELEMENT_NOT_FOUND
    }

    override fun remove(element: E?): E? {
        return remove(indexOf(element))
    }

    private fun getNode(index: Int): Node<E>? {
        rangeCheck(index)
        if (index < (size shr 1)) {
            var node = first
            for (i in 0 until index) {
                node = node!!.next
            }
            return node
        } else {
            var node = last
            for (i in size - 1 downTo index + 1) {
                node = node!!.prev
            }
            return node
        }
    }

    override fun toString(): String {
        val string = StringBuilder()
        string.append("size = ").append(size).append(", [")
        var node = first
        for (i in 0 until size) {
            if (i != 0) {
                string.append(", ")
            }
            string.append(node)
            node = node!!.next
        }
        string.append("]")
        return string.toString()
    }
}
