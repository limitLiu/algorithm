package wiki.mdzz.utils.queue

import wiki.mdzz.utils.heap.BinaryHeap

class PriorityQueue<E>(comparator: Comparator<E>? = null) {
    private var heap = BinaryHeap<E>()

    init {
        if (comparator != null) {
            heap = BinaryHeap(comparator)
        }
    }

    fun size(): Int {
        return heap.size()
    }

    val isEmpty: Boolean
        get() = heap.isEmpty

    fun clear() {
        heap.clear()
    }

    fun offer(e: E?) {
        return heap.add(e)
    }

    fun poll(): E? {
        return heap.remove()
    }

}