package wiki.mdzz.utils.list

class SinglyLinkedList<E> : DefaultList<E>() {
    private var head: Node<E>? = null

    private inner class Node<E> constructor(var element: E?, var next: Node<E>?)

    init {
        head = Node(null, null)
    }

    override fun add(index: Int, element: E?): Boolean {
        rangeCheckForAdd(index)
        val prev = if (index == 0) {
            head
        } else {
            node(index - 1)
        }
        prev!!.next = Node(element, prev.next)
        ++size
        return true
    }

    override fun remove(index: Int): E? {
        rangeCheck(index)
        val prev = if (index == 0) {
            head
        } else {
            node(index - 1)
        }
        val removed = prev!!.next
        prev.next = removed!!.next
        --size
        return removed.element
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

    override fun indexOf(element: E?): Int {
        var tmp = head!!.next
        for (i in 0 until size()) {
            if (element == tmp!!.element) return i
            tmp = tmp.next
        }
        return ELEMENT_NOT_FOUND
    }

    override fun remove(element: E?): E? {
        return remove(index = indexOf(element))
    }

    override fun toString(): String {
        var curr = head!!.next
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
        var tmp = head!!.next
        for (i in 0 until index) {
            tmp = tmp!!.next
        }
        return tmp!!
    }

    /*
    override fun add(index: Int, element: E?) {
        rangeCheckForAdd(index)
        if (index == 0) {
            head = Node(element, head)
        } else {
            val prev = node(index - 1)
            prev.next = Node(element, prev.next)
        }
        ++size
    }

    override fun remove(index: Int): E? {
        rangeCheck(index)
        var removed = head
        if (index == 0) {
            head = head!!.next
        } else {
            val prev = node(index - 1)
            removed = prev.next
            prev.next = removed!!.next
        }
        --size
        return removed!!.element
    }

    private fun node(index: Int): Node<E> {
        rangeCheck(index)
        var tmp = head
        for (i in 0 until index) {
            tmp = tmp!!.next
        }
        return tmp!!
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

    override fun indexOf(element: E?): Int {
        var tmp = head
        for (i in 0 until size()) {
            if (element == tmp!!.element) return i
            tmp = tmp.next
        }
        return ELEMENT_NOT_FOUND
    }
    */
}
