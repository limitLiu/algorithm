package wiki.mdzz.utils.queue

class CircleDeque<E> {
    private var size = 0
    private var front = 0
    private var elements: Array<E?>
    fun clear() {
        for (i in 0 until size) {
            elements[idx(i)] = null
        }
        front = 0
        size = 0
    }

    private fun idx(i: Int): Int {
        val index = i + front
        return if (index < 0) index + elements.size else index % elements.size
    }

    fun size(): Int {
        return size
    }

    val isEmpty: Boolean
        get() = size() == 0

    fun rear(): E? {
        return elements[idx(size() - 1)]
    }

    fun front(): E? {
        return elements[front]
    }

    fun enqueueFront(e: E) {
        ensure(size() - 1)
        front = idx(-1)
        elements[front] = e
        size++
    }

    fun enqueueRear(element: E) {
        ensure(size() + 1)
        elements[idx(size)] = element
        size++
    }

    fun dequeueRear(): E? {
        val i = idx(size() - 1)
        val rear = elements[idx(i)]
        elements[i] = null
        return rear
    }

    fun dequeueFront(): E? {
        val frontE = elements[front]
        elements[front] = null
        front = idx(1)
        size--
        return frontE
    }

    override fun toString(): String {
        val str = StringBuilder()
        val len = elements.size
        str.append("capacity = ")
            .append(len)
            .append(" size = ").append(size)
            .append(" front = ").append(front)
            .append(", [")
        for (i in 0 until len) {
            if (i != 0) str.append(", ")
            str.append(elements[i])
        }
        str.append("]")
        return str.toString()
    }

    private fun ensure(capacity: Int) {
        val old = elements.size
        if (old >= capacity) return
        val newC = old + (old shr 1)
        val newElements = arrayOfNulls<Any>(newC) as Array<E?>
        for (i in 0 until size) {
            newElements[i] = elements[idx(i)]
        }
        elements = newElements
        front = 0
    }

    init {
        elements = arrayOfNulls<Any>(10) as Array<E?>
    }
}
