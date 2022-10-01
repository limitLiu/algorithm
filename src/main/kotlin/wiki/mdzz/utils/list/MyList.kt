package wiki.mdzz.utils.list

interface MyList<E> {
    val isEmpty: Boolean

    fun add(index: Int, element: E?): Boolean
    fun add(element: E?): Boolean
    fun remove(index: Int): E?
    operator fun set(index: Int, element: E?): E?
    operator fun get(index: Int): E?
    fun clear()
    operator fun contains(element: E?): Boolean
    fun indexOf(element: E?): Int
    fun size(): Int
    fun remove(element: E?): E?
}
