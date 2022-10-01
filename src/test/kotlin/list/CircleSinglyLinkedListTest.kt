package list

import wiki.mdzz.utils.list.CircleSinglyLinkedList
import org.junit.Assert.assertTrue
import org.junit.Test

class CircleSinglyLinkedListTest {
    @Test()
    fun testAdd() {
        val linkedList = CircleSinglyLinkedList<Int>()
        linkedList.add(10)
        linkedList.add(11)
        linkedList.add(12)
        linkedList.add(1, 10)
        assertTrue(linkedList[0] == 10)
    }

    @Test
    fun testSet() {
        val linkedList = CircleSinglyLinkedList<Int>()
        linkedList.add(10)
        linkedList.add(11)
        linkedList.add(12)
        linkedList.add(1, 10)
        val tmp = linkedList.set(3, 20)
        assertTrue(tmp == 12)
    }

    @Test
    fun testClear() {
        val linkedList = CircleSinglyLinkedList<Int>()
        linkedList.add(10)
        linkedList.add(11)
        linkedList.add(12)
        linkedList.add(1, 10)
        linkedList.clear()
        assertTrue(linkedList.isEmpty)
    }

    @Test
    fun testContains() {
        val linkedList = CircleSinglyLinkedList<Int>()
        linkedList.add(10)
        linkedList.add(11)
        linkedList.add(12)
        linkedList.add(1, 10)
        assertTrue(11 in linkedList)
    }

    @Test
    fun testIndexOf() {
        val linkedList = CircleSinglyLinkedList<Int>()
        assertTrue(linkedList.indexOf(30) == -1)
        linkedList.add(10)
        linkedList.add(11)
        linkedList.add(12)
        linkedList.add(1, 10)
        assertTrue(linkedList.indexOf(10) == 0)
    }

    @Test
    fun testRemove() {
        val linkedList = CircleSinglyLinkedList<Int>()
        linkedList.add(10)
        linkedList.add(30)
        linkedList.remove(element = 10)
        assertTrue(linkedList.indexOf(10) == -1)
        linkedList.remove(element = 30)
        assertTrue(linkedList.indexOf(30) == -1)
    }
}
