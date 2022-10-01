package wiki.mdzz.utils.list

class CircleSinglyLinkedList<E> : DefaultList<E>() {
    private var head: Node<E>? = null

    private inner class Node<E> constructor(var element: E?, var next: Node<E>?)

    override fun add(index: Int, element: E?): Boolean {
        rangeCheckForAdd(index)
        if (index == 0) {
            val newHead = Node(element, head)
            val tail = if (size() == 0) {
                newHead
            } else {
                node(size() - 1)
            }
            tail.next = newHead
            head = newHead
        } else {
            val prev = node(index - 1)
            prev.next = Node(element, prev.next)
        }
        ++size
        return true
    }

    override fun remove(index: Int): E? {
        rangeCheck(index)
        var removed = head
        if (index == 0) {
            if (size() == 1) {
                head = null
            } else {
                val tail = node(size() - 1)
                head = head!!.next
                tail.next = head
            }
        } else {
            val prev = node(index - 1)
            removed = prev.next
            prev.next = removed!!.next
        }
        --size
        return removed!!.element
    }

    override operator fun set(index: Int, element: E?): E? {
        val tmp = node(index)
        val old = tmp.element
        tmp.element = element
        return old
    }

    override fun get(index: Int): E? = node(index).element

    override fun clear() {
        size = 0
        head = null
    }

    override fun contains(element: E?): Boolean = indexOf(element) != ELEMENT_NOT_FOUND

    override fun remove(element: E?): E? {
        return remove(index = indexOf(element))
    }

    override fun toString(): String {
        var curr = head
        return if (curr == null) "[]"
        else {
            val sb = StringBuilder()
            sb.append('[')
            while (curr != null) {
                sb.append(curr.element)
                curr = curr.next
                if (curr?.element == null) {
                    sb.append(']')
                } else {
                    sb.append(',').append(' ')
                }
            }
            sb.toString()
        }
    }

    private fun node(index: Int): Node<E> {
        rangeCheck(index)
        var tmp = head
        for (i in 0 until index) {
            tmp = tmp!!.next
        }
        return tmp!!
    }

    override fun indexOf(element: E?): Int {
        var tmp = head
        for (i in 0 until size()) {
            if (element == tmp!!.element) return i
            tmp = tmp.next
        }
        return ELEMENT_NOT_FOUND
    }
}
