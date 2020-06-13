package ltd.rust_lang.utils.list

import java.util.*

class Vec<E>(capacity: Int = DEFAULT_CAPACITY) : DefaultList<E>() {
    private var elements: Array<Any?>

    init {
        this.elements = arrayOfNulls(capacity)
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
        val old = elements[index] as E?

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
        val old = elements[index] as E?
        elements[index] = element
        return old
    }

    override fun get(index: Int): E? {
        rangeCheck(index)
        return elements[index] as E?
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

    fun toArray(): Array<out Any?> {
        return elements.copyOf(size)
    }

    private fun ensure(capacity: Int) {
        val old = elements.size
        if (old >= capacity) return
        // old + (old >> 1)
        val newCap = old + (old shr 1)
        val newElements = arrayOfNulls<Any>(newCap)
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
        val newElements = arrayOfNulls<Any>(newCap)
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
