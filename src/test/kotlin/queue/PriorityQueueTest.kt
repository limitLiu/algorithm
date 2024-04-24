package queue

import org.junit.Assert
import org.junit.Test
import net.wu_chinese.entities.Person
import net.wu_chinese.utils.queue.PriorityQueue

class PriorityQueueTest {
    @Test
    fun testAdd() {
        val queue = PriorityQueue<Int>()
        queue.offer(10)
        queue.offer(11)
        Assert.assertTrue(queue.poll() == 11)
        Assert.assertTrue(queue.poll() == 10)
        Assert.assertTrue(queue.isEmpty)
        val personQueue = PriorityQueue<Person> { t1, t2 ->
            return@PriorityQueue t1.age - t2.age
        }
        val person = Person(12, "test1")
        personQueue.offer(Person(11, "test0"))
        personQueue.offer(person)
        personQueue.offer(Person(14, "test2"))
        Assert.assertTrue(personQueue.poll() == Person(14, "test2"))
        Assert.assertTrue(personQueue.poll() == person)
        Assert.assertTrue(!personQueue.isEmpty)
    }
}