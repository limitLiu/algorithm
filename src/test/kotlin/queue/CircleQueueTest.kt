package queue

import net.wu_chinese.utils.queue.CircleQueue
import org.junit.Assert.assertTrue
import org.junit.Test

class CircleQueueTest {
    @Test
    fun test() {
        val queue = CircleQueue<Int?>()
        // 0 1 2 3 4 5 6 7 8 9
        // 0 1 2 3 4 5 6 7 8 9
        for (i in 0..9) {
            queue.enqueue(i)
        }
        // null null null null null 5 6 7 8 9
        // null null null null null 5 6 7 8 9
        for (i in 0..4) {
            queue.dequeue()
        }
        // 15 16 17 18 19 5 6 7 8 9
        // 15 16 17 18 19 5 6 7 8 9
        for (i in 15..19) {
            queue.enqueue(i)
        }
        assertTrue(queue.dequeue() == 5)
        assertTrue(queue.dequeue() == 6)
    }
}
