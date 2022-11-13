package wiki.mdzz.algorithm.sort

class Heap<T : Comparable<T>> : Sort<T>() {
    private var size = 0

    override fun sort() {
        size = vec!!.size()
        for (i in (size shr 1) - 1 downTo 0) {
            siftDown(i)
        }
        while (size > 1) {
            swap(0, --size)
            siftDown(0)
        }
    }

    private fun siftDown(index: Int) {
        var i = index
        val element = vec!![i]
        val half = size shr 1
        while (i < half) {
            var childIndex = (i shl 1) + 1
            var child = vec!![childIndex]
            val rightIndex = childIndex + 1 // (index shl 1) + 2
            if (rightIndex < size && compare(vec!![rightIndex]!!, child!!) > 0) {
                child = vec!![rightIndex.also { childIndex = it }]
            }

            if (compare(element!!, child!!) >= 0) break
            vec!![i] = child
            i = childIndex
        }
        vec!![i] = element
    }
}