package wiki.mdzz.utils.heap

interface Heap<E> {
    fun size(): Int
    val isEmpty: Boolean
    fun clear()
    fun add(element: E?)
    fun get(): E
    fun remove(): E
    fun replace(element: E?): E?
}