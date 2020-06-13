package ltd.rust_lang.utils.list

class CircleLinkedList<E> : DefaultList<E>() {

    private var head: Node<E>? = null
    private var tail: Node<E>? = null

    private var current: Node<E>? = null

    private inner class Node<E> constructor(
        var element: E?,
        var prev: Node<E>?,
        var next: Node<E>?
    )

    fun reset() {
        current = head
    }

    fun next(): E? {
        if (current == null) return null
        current = current?.next
        return current?.element
    }

    override fun add(index: Int, element: E?): Boolean {
        rangeCheckForAdd(index)
        if (index == size()) {
            val old = tail
            tail = Node(element, old, head)
            if (old != null) {
                old.next = tail
            } else {
                head = tail
                head?.next = head
            }
            head?.prev = tail
        } else {
            val current = node(index)
            val prev = current.prev
            val newNode = Node(element, prev, current)
            current.prev = newNode
            prev?.next = newNode
            // index == 0
            if (current == head) {
                head = newNode
            }
        }
        ++size
        return true
    }

    override fun remove(index: Int): E? {
        return remove(node(index))
    }

    fun remove(): E? {
        if (null == current) return null
        val next = current!!.next
        val item = remove(current!!)
        current = if (size() == 0) {
            null
        } else {
            next
        }
        return item
    }

    private fun remove(node: Node<E>): E? {
        if (size() == 1) {
            head = null
            tail = null
        } else {
            val prev = node.prev
            val next = node.next

            prev!!.next = next
            next!!.prev = prev

            // index == 0
            if (head == node) {
                head = next
            }

            // index == size() - 1
            if (tail == node) {
                tail = prev
            }
        }
        --size
        return node.element
    }


    override operator fun set(index: Int, element: E?): E? {
        val current = node(index)
        val old = current.element
        current.element = element
        return old
    }

    override fun get(index: Int): E? = node(index).element

    override fun clear() {
        size = 0
        head = null
        tail = null
    }

    override fun contains(element: E?): Boolean = indexOf(element) != ELEMENT_NOT_FOUND

    override fun indexOf(element: E?): Int {
        var tmp = head
        for (i in 0 until size()) {
            if (element == tmp!!.element) return i
            tmp = tmp.next
        }
        return ELEMENT_NOT_FOUND
    }

    override fun remove(element: E?): E? {
        return remove(index = indexOf(element))
    }

    private fun node(index: Int): Node<E> {
        rangeCheck(index)
        return if (index < (size shr 1)) {
            var tmp = head
            for (i in 0 until index) {
                tmp = tmp!!.next
            }
            tmp!!
        } else {
            var tmp = tail
            for (i in size - 1 downTo index + 1) {
                tmp = tmp!!.prev
            }
            tmp!!
        }
    }

    override fun toString(): String {
        var curr = head
        if (curr == null) return "[]"
        else {
            val sb = StringBuilder()
            sb.append('[')
            for (i in 0 until size()) {
                sb.append(curr!!.element)
                curr = curr.next
                if (curr == head) {
                    sb.append(']')
                } else {
                    sb.append(',').append(' ')
                }
            }
            return sb.toString()
        }
    }
}
