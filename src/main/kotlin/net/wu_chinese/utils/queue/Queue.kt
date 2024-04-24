package net.wu_chinese.utils.queue

import net.wu_chinese.utils.list.LinkedList

class Queue<E> {
    private val list = LinkedList<E>()

    fun peek(): E? {
        return list[0]
    }

    fun size(): Int {
        return list.size()
    }

    val isEmpty: Boolean
        get() = list.isEmpty

    fun offer(e: E?): Boolean {
        return list.add(e)
    }

    fun poll(): E? {
        return list.remove(0)
    }

    fun clear() {
        list.clear()
    }
}
