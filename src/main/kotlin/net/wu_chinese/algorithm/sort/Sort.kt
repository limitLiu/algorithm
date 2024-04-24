package net.wu_chinese.algorithm.sort

import net.wu_chinese.utils.list.Vec

abstract class Sort<T : Comparable<T>> {
    protected var vec: Vec<T>? = null

    fun sort(vec: Vec<T>?) {
        if (vec == null || vec.size() < 2) return
        this.vec = vec
        sort()
    }

    protected abstract fun sort()

    protected fun compare(lhs: Int, rhs: Int): Int {
        return vec!![lhs]!!.compareTo(vec!![rhs]!!)
    }

    protected fun compare(lhs: T, rhs: T): Int {
        return lhs.compareTo(rhs)
    }

    protected fun swap(lhs: Int, rhs: Int)  {
        val temp = vec!![lhs]
        vec!![lhs] = vec!![rhs]
        vec!![rhs] = temp
    }

    protected fun move(lhs: Int, rhs: Int) {
        vec!![lhs] = vec!![rhs]
    }
}
