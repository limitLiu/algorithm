package wiki.mdzz.algorithm.sort

class Selection<T : Comparable<T>> : Sort<T>() {
    override fun sort() {
        for (end in vec!!.size() - 1 downTo 1) {
            var maxIndex = 0
            for (i in 1..end) {
                if (compare(maxIndex, i) < 1) {
                    maxIndex = i
                }
            }
            swap(maxIndex, end)
        }
    }
}