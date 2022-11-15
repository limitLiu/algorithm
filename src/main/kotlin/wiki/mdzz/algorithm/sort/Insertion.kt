package wiki.mdzz.algorithm.sort

class Insertion<T : Comparable<T>> : Sort<T>() {
    override fun sort() {
        for (i in 1 until vec!!.size()) {
            var current = i
            while (current > 0 && compare(current, current - 1) < 0) {
                swap(current, current - 1)
                --current
            }
        }
    }
}