package wiki.mdzz.utils.list

abstract class DefaultList<E> : MyList<E> {
    companion object {
        const val DEFAULT_CAPACITY = 10
        const val ELEMENT_NOT_FOUND = -1
    }

    protected var size: Int = 0

    override fun size(): Int {
        return size
    }

    override val isEmpty: Boolean
        get() = size() == 0

    private fun throwIndexOutOfBoundsException(index: Int): Unit =
        throw IndexOutOfBoundsException("index: $index, size: $size")

    override fun add(element: E?): Boolean {
        add(size(), element)
        return true
    }

    protected open fun rangeCheckForAdd(index: Int) {
        if (index > size() || index < 0) throwIndexOutOfBoundsException(index)
    }

    protected open fun rangeCheck(index: Int) {
        if (index >= size() || index < 0) throwIndexOutOfBoundsException(index)
    }
}

