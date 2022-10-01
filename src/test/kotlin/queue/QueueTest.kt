package queue

import wiki.mdzz.utils.queue.Queue
import org.junit.Assert.assertTrue
import org.junit.Test

class QueueTest {
    @Test
    fun testAdd() {
        val queue = Queue<Int>()
        queue.offer(10)
        assertTrue(queue.poll() == 10)
        assertTrue(queue.isEmpty)
    }

    @Test
    fun testPeek() {
        val queue = Queue<Int>()
        queue.offer(10)
        assertTrue(queue.peek() == 10)
        assertTrue(!queue.isEmpty)
    }
}
