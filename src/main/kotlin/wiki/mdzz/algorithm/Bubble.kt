package wiki.mdzz.algorithm

import wiki.mdzz.utils.list.Vec

class Bubble {
    companion object {
        fun <T : Comparable<T>> sort(vec: Vec<T>): Vec<T> {
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
    }
}