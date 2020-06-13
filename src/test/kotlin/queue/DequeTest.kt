package queue

import ltd.rust_lang.utils.queue.Deque
import org.junit.Assert.assertTrue
import org.junit.Test

class DequeTest {
    @Test
    fun test() {
        val queue = Deque<Int>()
        queue.offerLast(10)
        queue.offerLast(20)
        assertTrue(!queue.isEmpty())
        queue.offerFirst(11)
        assertTrue(queue.peekFirst() == 11)
        assertTrue(queue.pollLast() == 20)
        queue.clear()
        assertTrue(queue.isEmpty())
    }
}
