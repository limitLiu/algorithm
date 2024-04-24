package queue

import net.wu_chinese.utils.queue.CircleDeque
import org.junit.Assert
import org.junit.Test

class CircleDequeTest {
    @Test
    fun test() {
        val queue = CircleDeque<Int>()
        // head5 4 3 2 1  100 101 102 103 104 105 106 8 7 6 tail

        // head 8 7 6  5 4 3 2 1  100 101 102 103 104 105 106 107 108 109 null null 10 9 tail
        // head 5 4 3 2 1  100 101 102 103 104 105 106 8 7 6 tail

        // head 8 7 6  5 4 3 2 1  100 101 102 103 104 105 106 107 108 109 null null 10 9 tail
        for (i in 0..9) {
            queue.enqueueFront(i + 1)
            queue.enqueueRear(i + 100)
        }

        // head null 7 6  5 4 3 2 1  100 101 102 103 104 105 106 null null null null null null null tail
        // head null 7 6  5 4 3 2 1  100 101 102 103 104 105 106 null null null null null null null tail
        for (i in 0..2) {
            queue.dequeueFront()
            queue.dequeueRear()
        }

        // head 11 7 6  5 4 3 2 1  100 101 102 103 104 105 106 null null null null null null 12 tail
        // head 11 7 6  5 4 3 2 1  100 101 102 103 104 105 106 null null null null null null 12 tail
        queue.enqueueFront(11)
        queue.enqueueFront(12)
        Assert.assertTrue(queue.dequeueFront() == 12)
        Assert.assertTrue(queue.dequeueFront() == 11)
        Assert.assertTrue(queue.dequeueFront() == 7)
        Assert.assertTrue(queue.dequeueFront() == 6)
        Assert.assertTrue(queue.dequeueFront() == 5)
        Assert.assertTrue(queue.dequeueFront() == 4)
        Assert.assertTrue(queue.dequeueFront() == 3)
        Assert.assertTrue(queue.dequeueFront() == 2)
        Assert.assertTrue(queue.dequeueFront() == 1)
        Assert.assertTrue(queue.dequeueFront() == 100)
        Assert.assertTrue(queue.dequeueFront() == 101)
        Assert.assertTrue(queue.dequeueFront() == 102)
        Assert.assertTrue(queue.dequeueFront() == 103)
        Assert.assertTrue(queue.dequeueFront() == 6)
        Assert.assertTrue(queue.dequeueFront() == 105)
        Assert.assertTrue(queue.dequeueFront() == 106)
        Assert.assertTrue(queue.dequeueFront() == 107)
        Assert.assertTrue(queue.dequeueFront() == 108)
        queue.dequeueFront()
        Assert.assertTrue(queue.isEmpty)
    }
}
