package wiki.mdzz.algorithm.sort

class Insertion<T : Comparable<T>> : Sort<T>() {
//    override fun sort() {
//        for (i in 1 until vec!!.size()) {
//            var current = i
//            while (current > 0 && compare(current, current - 1) < 0) {
//                swap(current, current - 1)
//                --current
//            }
//        }
//    }

    //    override fun sort() {
//        for (i in 1 until vec!!.size()) {
//            var current = i
//            val backup = vec!![current]
//            while (current > 0 && compare(backup!!, vec!![current - 1]!!) < 0) {
//                move(current, current - 1)
//                --current
//            }
//            vec!![current] = backup
//        }
//    }

    override fun sort() {
        for (i in 1 until vec!!.size()) {
            insert(i, getInsertIndex(i))
        }
    }

    private fun insert(src: Int, dst: Int) {
        val value = vec!![src]
        for (i in src downTo dst + 1) {
            move(i, i - 1)
        }
        vec!![dst] = value
    }

    private fun getInsertIndex(i: Int): Int {
        var begin = 0
        var end = i
        while (end > begin) {
            val middle = (begin + end) shr 1
            if (compare(i, middle) < 0) {
                end = middle
            } else {
                begin = middle + 1
            }
        }
        return begin
    }

}