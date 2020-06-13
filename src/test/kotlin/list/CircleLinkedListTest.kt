package list

import ltd.rust_lang.utils.list.CircleLinkedList
import org.junit.Assert.assertTrue
import org.junit.Test

class CircleLinkedListTest {
    @Test()
    fun testAdd() {
        val linkedList = CircleLinkedList<Int>()
        linkedList.add(10)
        linkedList.add(11)
        linkedList.add(12)
        linkedList.add(1, 10)
        assertTrue(linkedList[0] == 10)
    }

    @Test
    fun testSet() {
        val linkedList = CircleLinkedList<Int>()
        linkedList.add(10)
        linkedList.add(11)
        linkedList.add(12)
        linkedList.add(1, 10)
        val tmp = linkedList.set(3, 20)
        assertTrue(tmp == 12)
    }

    @Test
    fun testClear() {
        val linkedList = CircleLinkedList<Int>()
        linkedList.add(10)
        linkedList.add(11)
        linkedList.add(12)
        linkedList.add(1, 10)
        linkedList.clear()
        assertTrue(linkedList.isEmpty)
    }

    @Test
    fun testContains() {
        val linkedList = CircleLinkedList<Int>()
        linkedList.add(10)
        linkedList.add(11)
        linkedList.add(12)
        linkedList.add(1, 10)
        assertTrue(11 in linkedList)
    }

    @Test
    fun testIndexOf() {
        val linkedList = CircleLinkedList<Int>()
        assertTrue(linkedList.indexOf(30) == -1)
        linkedList.add(10)
        linkedList.add(11)
        linkedList.add(12)
        linkedList.add(1, 10)
        assertTrue(linkedList.indexOf(10) == 0)
    }

    @Test
    fun testRemove() {
        val linkedList = CircleLinkedList<Int>()
        linkedList.add(10)
        linkedList.add(30)
        linkedList.remove(element = 10)
        assertTrue(linkedList.indexOf(10) == -1)
        linkedList.remove(element = 30)
        assertTrue(linkedList.indexOf(30) == -1)
    }

    @Test
    fun testJosephs() {
        val linkedList = CircleLinkedList<Int>()
        for (i in 1..8) {
            linkedList.add(i)
        }
        linkedList.reset()
        var item = 0
        while (!linkedList.isEmpty) {
            linkedList.next()
            linkedList.next()
            item = linkedList.remove()!!
        }
        assertTrue(item == 7)
    }
}
