package wiki.mdzz.algorithm.sort

import wiki.mdzz.utils.list.Vec

class BinarySearch {
    companion object {
        fun <T : Comparable<T>> indexOf(vec: Vec<T>?, value: T): Int {
            if (vec == null || vec.size() == 0) return -1
            var begin = 0
            var end = vec.size()
            while (end > begin) {
                val middle = (begin + end) shr 1
                if (value < vec[middle]!!) {
                    end = middle
                } else if (value > vec[middle]!!) {
                    begin = middle + 1
                } else {
                    return middle
                }
            }
            return -1
        }

        fun <T : Comparable<T>> getInsertIndex(vec: Vec<T>?, value: T): Int {
            if (vec == null || vec.size() == 0) return -1
            var begin = 0
            var end = vec.size()
            while (end > begin) {
                val middle = (begin + end) shr 1
                if (value < vec[middle]!!) {
                    end = middle
                } else {
                    begin = middle + 1
                }
            }
            return begin
        }
    }
}