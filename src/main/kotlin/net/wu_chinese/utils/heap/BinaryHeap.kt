package net.wu_chinese.utils.heap

import net.wu_chinese.printer.BinaryTreeInfo
import kotlin.math.max

class BinaryHeap<E> : AbstractHeap<E>, BinaryTreeInfo {
    companion object {
        const val DEFAULT_CAPACITY = 10

        fun <E> heapOf(vararg args: E?): Heap<E> {
            return BinaryHeap(args as Array<E?>)
        }
    }

    private var elements: Array<E?>

    constructor(elements: Array<E?>? = null) : this(elements, null)

    constructor(comparator: Comparator<E>) : this(null, comparator)

    constructor(
        elements: Array<E?>?,
        comparator: Comparator<E>?,
        capacity: Int = DEFAULT_CAPACITY
    ) : super(comparator) {
        if (elements.isNullOrEmpty()) {
            this.elements = arrayOfNulls<Any?>(capacity) as Array<E?>
        } else {
            size = elements.size
            val cap = max(size, capacity)
            this.elements = arrayOfNulls<Any?>(cap) as Array<E?>
            System.arraycopy(elements, 0, this.elements, 0, size())
            heapify()
        }
    }

    override fun clear() {
        for (i in 0 until size) {
            elements[i] = null
        }
        size = 0
    }

    override fun add(element: E?) {
        nullCheck(element)
        ensure(size() + 1)
        elements[size++] = element
        siftUp(size - 1)
    }

    override fun get(): E {
        emptyCheck()
        return elements[0]!!
    }

    override fun remove(): E {
        emptyCheck()
        val tail = --size
        val root = elements[0]
        elements[0] = elements[tail]
        elements[tail] = null
        siftDown(0)
        return root!!
    }

    override fun replace(element: E?): E? {
        nullCheck(element)
        var root: E? = null
        if (isEmpty) {
            elements[0] = element
            size++
        } else {
            root = elements[0]
            elements[0] = element
            siftDown(0)
        }
        return root
    }

    private fun siftUp(index: Int) {
        var i = index
        val node = elements[i]
        while (i > 0) {
            val parentIndex = (i - 1) shr 1
            val parent = elements[parentIndex]
            if (compare(node!!, parent!!) <= 0) break
            elements[i] = parent
            i = parentIndex
        }
        elements[i] = node
    }

    private fun heapify() {
//        for (i in 1 until size()) {
//            siftUp(i)
//        }
        for (i in (size() shr 1) - 1 downTo 0) {
            siftDown(i)
        }
    }

    private fun siftDown(index: Int) {
        var i = index
        val element = elements[i]
        val half = size() shr 1
        while (i < half) {
            var childIndex = (i shl 1) + 1
            var child = elements[childIndex]
            val rightIndex = childIndex + 1 // (index shl 1) + 2
            if (rightIndex < size() && compare(elements[rightIndex]!!, child!!) > 0) {
                child = elements[rightIndex.also { childIndex = it }]
            }

            if (compare(element!!, child!!) >= 0) break
            elements[i] = child
            i = childIndex
        }
        elements[i] = element
    }

    private fun nullCheck(element: E?) {
        if (element == null)
            throw IllegalArgumentException("Element cannot be null.")
    }

    private fun emptyCheck() {
        if (isEmpty)
            throw IndexOutOfBoundsException("Heap is empty.")
    }

    private fun ensure(capacity: Int) {
        val old = elements.size
        if (old >= capacity) return
        // old + (old >> 1)
        val newCap = old + (old shr 1)
        val newElements = arrayOfNulls<Any?>(newCap) as Array<E?>
        if (size() >= 0) {
            System.arraycopy(elements, 0, newElements, 0, size())
        }
        elements = newElements
    }

    override fun root(): Int {
        return 0
    }

    override fun left(node: Any?): Any? {
        val index = ((node as Int) shl 1) + 1
        return if (index >= size()) {
            null
        } else {
            index
        }
    }

    override fun right(node: Any?): Any? {
        val index = ((node as Int) shl 1) + 2
        return if (index >= size()) {
            null
        } else {
            index
        }
    }

    override fun string(node: Any?): Any {
        return elements[node as Int]?.toString() ?: ""
    }
}
