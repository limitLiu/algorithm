package wiki.mdzz.algorithm

import wiki.mdzz.utils.list.Vec

class Bubble {
    companion object {
        fun <T : Comparable<T>> sort1(vec: Vec<T>): Vec<T> {
            for (end in vec.size() - 1 downTo 1) {
                for (i in 1..end) {
                    if (vec[i]!! < vec[i - 1]!!) {
                        val temp = vec[i]
                        vec[i] = vec[i - 1]
                        vec[i - 1] = temp
                    }
                }
            }
            return vec
        }

        fun <T : Comparable<T>> sort2(vec: Vec<T>): Vec<T> {
            for (end in vec.size() - 1 downTo 1) {
                var sorted = true
                for (i in 1..end) {
                    if (vec[i]!! < vec[i - 1]!!) {
                        val temp = vec[i]
                        vec[i] = vec[i - 1]
                        vec[i - 1] = temp
                        sorted = false
                    }
                }
                if (sorted) break
            }
            return vec
        }

        fun <T : Comparable<T>> sort3(vec: Vec<T>): Vec<T> {
            var end: Int = vec.size() - 1
            while (end > 0) {
                var index = 1
                for (i in 1..end) {
                    if (vec[i]!! < vec[i - 1]!!) {
                        val temp = vec[i]
                        vec[i] = vec[i - 1]
                        vec[i - 1] = temp
                        index = i
                    }
                }
                end = index
                end--
            }
            return vec
        }

    }
}