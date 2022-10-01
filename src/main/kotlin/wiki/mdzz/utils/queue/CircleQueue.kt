package wiki.mdzz.utils.queue

class CircleQueue<E>(capacity: Int? = 10) {
    private var front: Int = 0
    private var size = 0
    private var elements: Array<Any?> = arrayOfNulls(capacity!!)

    fun enqueue(e: E?) {
        ensure(size + 1)
        elements[idx(size)] = e
        size++
    }

    fun dequeue(): E? {
        val frontE = elements[front]
        elements[front] = null
        front = idx(1)
        size--
        return frontE as E?
    }

    fun peek(): E? {
        return elements[front] as E?
    }

    fun clear() {
        for (i in 0 until size) {
            elements[idx(i)] = null
        }
        front = 0
        size = 0
    }

    fun size(): Int {
        return size
    }

    fun isEmpty(): Boolean {
        return size() == 0
    }

    private fun ensure(cap: Int) {
        val old = elements.size
        if (old >= cap) return
        val newC = old + (old shr 1)
        val newElements = arrayOfNulls<Any?>(newC)
        for (i in 0 until size) {
            newElements[i] = elements[idx(i)]
        }
        elements = newElements
        front = 0
    }

    override fun toString(): String {
        val str = StringBuilder()
        val len: Int = elements.size
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

    private fun idx(i: Int): Int {
        return (front + i) % elements.size
    }
}
