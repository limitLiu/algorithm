package wiki.mdzz.utils.stack

interface IStack<E> {
    fun pop(): E?
    fun push(e: E?)
    fun size(): Int
    val isEmpty: Boolean

    fun peek(): E?
    fun clear()
}
