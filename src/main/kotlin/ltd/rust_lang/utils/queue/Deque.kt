package ltd.rust_lang.utils.queue

import ltd.rust_lang.utils.list.LinkedList

class Deque<E> {
    private val list = LinkedList<E>()

    fun size(): Int {
        return list.size()
    }

    fun isEmpty(): Boolean {
        return list.isEmpty
    }

    fun offerFirst(e: E?): Boolean {
        return list.add(0, e)
    }

    fun offerLast(e: E?): Boolean {
        return list.add(e)
    }

    fun pollFirst(): E? {
        return list.remove(0)
    }

    fun pollLast(): E? {
        return list.remove(size() - 1)
    }

    fun peekFirst(): E? {
        return list[0]
    }

    fun peekLast(): E? {
        return list[size() - 1]
    }

    fun clear() {
        list.clear()
    }

    override fun toString(): String {
        return list.toString()
    }
}
