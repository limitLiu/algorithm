package net.wu_chinese.utils.heap

abstract class AbstractHeap<E>() : Heap<E> {
    protected var size: Int = 0

    override fun size(): Int {
        return size
    }

    private var comparator: Comparator<E>? = null

    override val isEmpty: Boolean
        get() = size() == 0

    constructor(comparator: Comparator<E>?) : this() {
        this.comparator = comparator
    }

    protected fun compare(e1: E, e2: E): Int {
        return comparator?.compare(e1, e2) ?: (e1 as Comparable<E>).compareTo(e2)
    }
}
