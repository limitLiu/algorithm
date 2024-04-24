package net.wu_chinese.utils.union

class UnionFind(capacity: Int) {
    private var parents: Array<Int>

    init {
        if (capacity < 0) {
            throw IllegalArgumentException("Capacity must be >= 1")
        }
        parents = Array(capacity) { it }.mapIndexed { index, _ -> index }.toTypedArray()
    }

    fun find(v: Int): Int {
        rangeCheck(v)
        return parents[v]
    }

    fun isSame(lhs: Int, rhs: Int): Boolean {
        return find(lhs) == find(rhs)
    }

    fun union(lhs: Int, rhs: Int) {
        val parent1 = find(lhs)
        val parent2 = find(rhs)
        for (i in parents.indices) {
            if (parents[i] == parent1) {
                parents[i] = parent2
            }
        }
    }

    private fun rangeCheck(v: Int) {
        if (v < 0 || v >= parents.size) {
            throw IndexOutOfBoundsException("index is out of bounds")
        }
    }
}