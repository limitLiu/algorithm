package net.wu_chinese.utils.set

import net.wu_chinese.utils.list.DefaultList.Companion.ELEMENT_NOT_FOUND
import net.wu_chinese.utils.list.LinkedList

class ListSet<E> : Set<E> {
    private val container = LinkedList<E>()

    override fun size(): Int {
        return container.size()
    }

    override val isEmpty: Boolean
        get() = size() == 0

    override fun clear() {
        container.clear()
    }

    override fun contains(element: E?): Boolean {
        return element in container
    }

    override fun add(element: E?) {
        val idx = container.indexOf(element)
        if (idx != ELEMENT_NOT_FOUND) {
            container[idx] = element
        } else {
            container.add(element)
        }
    }

    override fun remove(element: E?) {
        container.remove(element = element)
    }

    override fun traversal(visitor: ((e: E?) -> Boolean)?) {
        for (i in 0 until container.size()) {
            if (visitor != null) {
                visitor(container[i])
            }
        }
    }

    override fun toString(): String {
        return container.toString()
    }
}
