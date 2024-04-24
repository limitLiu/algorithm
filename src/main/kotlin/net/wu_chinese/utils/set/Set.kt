package net.wu_chinese.utils.set

interface Set<E> {

    fun size(): Int
    val isEmpty: Boolean

    fun clear()

    fun contains(element: E?): Boolean

    fun add(element: E?)

    fun remove(element: E?)

    fun traversal(visitor: ((e: E?) -> Boolean)?)
}
