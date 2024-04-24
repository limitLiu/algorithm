package heap

import org.junit.Assert
import org.junit.Test
import net.wu_chinese.printer.BinaryTrees
import net.wu_chinese.utils.heap.BinaryHeap

class HeapTest {
    @Test
    fun testHeapAdd() {
        val heap = BinaryHeap<Int>()
        heap.add(10)
        heap.add(11)
        heap.add(12)
        Assert.assertTrue(heap.get() == 12)
        Assert.assertTrue(heap.size() == 3)
    }

    @Test
    fun testHeapRemove() {
        val heap = BinaryHeap<Int>()
        heap.add(10)
        heap.add(11)
        heap.add(12)
        Assert.assertTrue(heap.remove() == 12)
        Assert.assertTrue(heap.size() == 2)
        Assert.assertTrue(heap.get() == 11)
    }

    @Test
    fun testHeapBatchAdd() {
        val heap = BinaryHeap.heapOf(88, 44, 53, 41, 16, 6, 70, 18, 85, 99, 81, 23, 36, 43, 37)
        Assert.assertTrue(heap.remove() == 99)
        Assert.assertTrue(heap.size() == 14)
        Assert.assertTrue(heap.get() == 88)
    }

    @Test
    fun testMinHeap() {
        val heap = BinaryHeap(
            arrayOf(88, 44, 53, 41, 16, 6, 70, 18, 85, 99, 81, 23, 36, 43, 37),
            { t1, t2 -> t2 - t1 })
        Assert.assertTrue(heap.remove() == 6)
        Assert.assertTrue(heap.size() == 14)
        Assert.assertTrue(heap.get() == 16)
    }

    @Test
    fun testTopK() {
        val k = 5
        val data = arrayOf(88, 44, 53, 41, 16, 6, 70, 18, 85, 99, 81, 23, 36, 43, 37)
        val heap = BinaryHeap<Int> { t1, t2 -> t2 - t1 }
        data.forEach { e ->
            if (heap.size() < k) {
                heap.add(e)
            } else if (heap.get() < e) {
                heap.replace(e)
            }
        }
        Assert.assertTrue(heap.remove() == 70)
        Assert.assertTrue(heap.size() == 4)
        Assert.assertTrue(heap.get() == 81)
        BinaryTrees.println(heap)
    }
}
