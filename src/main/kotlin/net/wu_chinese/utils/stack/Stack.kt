package net.wu_chinese.utils.stack

import net.wu_chinese.utils.list.Vec

class Stack<E> : IStack<E> {
    private val vec = Vec<E>()

    override fun pop(): E? {
        return vec.remove(size() - 1)
    }

    override fun push(e: E?) {
        vec.add(e)
    }

    override fun size(): Int {
        return vec.size()
    }

    override val isEmpty: Boolean
        get() = vec.isEmpty

    override fun peek(): E? {
        return vec[size() - 1]
    }

    override fun clear() {
        vec.clear()
    }
}
