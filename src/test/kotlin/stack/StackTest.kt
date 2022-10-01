package stack

import wiki.mdzz.utils.stack.Stack
import org.junit.Test
import org.junit.Assert.assertTrue

class StackTest {
    @Test
    fun testPush() {
        val stack = Stack<Int>()
        stack.push(10)
        stack.push(11)
        assertTrue(stack.peek() == 11)
    }

    @Test
    fun testPop() {
        val stack = Stack<Int>()
        stack.push(10)
        assertTrue(stack.pop() == 10)
    }

    @Test
    fun testTop() {
        val stack = Stack<Int>()
        stack.push(10)
        assertTrue(stack.peek() == 10)
    }


    @Test
    fun testSize() {
        val stack = Stack<Int>()
        assertTrue(stack.size() == 0)
        stack.push(111)
        assertTrue(stack.size() == 1)
    }

    @Test
    fun testClear() {
        val stack = Stack<Int>()
        stack.push(111)
        assertTrue(!stack.isEmpty)
        stack.clear()
        assertTrue(stack.isEmpty)
    }
}
