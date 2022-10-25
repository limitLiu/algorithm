package wiki.mdzz.utils.list

class Vec<E> : DefaultList<E> {
    companion object {
        fun <E> arrayOf(vararg args: E?): Vec<E> {
            return Vec(args as Array<E?>)
        }
    }

    private var elements: Array<E?>
    private var capacity = DEFAULT_CAPACITY

    constructor(capacity: Int) {
        this.capacity = capacity
        this.elements = arrayOfNulls<Any>(capacity) as Array<E?>
    }

    constructor(elements: Array<E?>? = null) : this(DEFAULT_CAPACITY) {
        this.elements = arrayOfNulls<Any?>(
            elements?.size ?: capacity
        ) as Array<E?>
        if (elements != null) {
            size = elements.size
            System.arraycopy(elements, 0, this.elements, 0, size)
        }
    }

    override fun add(index: Int, element: E?): Boolean {
        if (element == null) return false
        rangeCheckForAdd(index)
        ensure(size() + 1)

        /*
        if (size - index >= 0) System.arraycopy(elements, index, elements, index + 1, size - index)
        for (int i = size - 1; i >= index; --i) {
            elements[i + 1] = elements[i];
        }
        */

        for (i in size() - 1 downTo index) {
            elements[i + 1] = elements[i]
        }

        elements[index] = element
        ++size
        return true
    }

    override fun remove(index: Int): E? {
        rangeCheck(index)
        val old = elements[index]

        /*
        for (int i = index + 1; i < size; ++i) {
            elements[i - 1] = elements[i];
        }
        */

        for (i in index + 1 until size) {
            elements[i - 1] = elements[i]
        }
        elements[--size] = null
        trim()
        return old
    }

    override fun remove(element: E?): E? {
        return remove(indexOf(element))
    }

    override operator fun set(index: Int, element: E?): E? {
        rangeCheck(index)
        val old = elements[index]
        elements[index] = element
        return old
    }

    override fun get(index: Int): E? {
        rangeCheck(index)
        return elements[index]
    }

    override fun clear() {
        for (i in 0 until size) {
            elements[i] = null
        }
        size = 0
    }

    override fun contains(element: E?): Boolean {
        return indexOf(element) != ELEMENT_NOT_FOUND
    }

    override fun indexOf(element: E?): Int {
        for (i in 0..size) {
            if (element == elements[i]) return i
        }
        return ELEMENT_NOT_FOUND
    }

    fun toArray(): Array<out E?> {
        return elements.copyOf(size)
    }

    private fun ensure(capacity: Int) {
        val old = elements.size
        if (old >= capacity) return
        // old + (old >> 1)
        val newCap = old + (old shr 1)
        val newElements = arrayOfNulls<Any>(newCap) as Array<E?>
        if (size() >= 0) {
//            System.arraycopy(elements, 0, newElements, 0, size())
            for (i in 0 until size()) {
                newElements[i] = elements[i]
            }
        }
        elements = newElements
    }

    private fun trim() {
        val old = elements.size
        // old >> 1
        val newCap = old shr 1
        if (size > newCap || old <= DEFAULT_CAPACITY) return
        val newElements = arrayOfNulls<Any>(newCap) as Array<E?>
        if (size() >= 0) {
//            System.arraycopy(elements, 0, newElements, 0, size())
            for (i in 0 until size()) {
                newElements[i] = elements[i]
            }
        }
        elements = newElements
    }

    override fun toString(): String = elements.contentToString()
}
