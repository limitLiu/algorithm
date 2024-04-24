package list

import net.wu_chinese.utils.list.Vec
import org.junit.Assert.assertTrue
import org.junit.Test

class VecTest {
    @Test
    fun testAdd() {
        val vec = Vec<Int>()
        vec.add(10)
        vec.add(11)
        vec.add(12)
        vec.add(1, 10)
        assertTrue(vec[0] == 10)
    }

    @Test
    fun testSet() {
        val vec = Vec<Int>()
        vec.add(10)
        vec.add(11)
        vec.add(12)
        vec.add(1, 10)
        val tmp = vec.set(2, 1)
        assertTrue(tmp == 11)
        vec[3] = 20
        assertTrue(vec[3] == 20)
    }

    @Test
    fun testClear() {
        val vec = Vec<Int>()
        vec.add(10)
        vec.add(11)
        vec.add(12)
        vec.add(1, 10)
        vec.clear()
        assertTrue(vec.isEmpty)
    }

    @Test
    fun testContains() {
        val vec = Vec<Int>()
        vec.add(10)
        vec.add(11)
        vec.add(12)
        vec.add(1, 10)
        assertTrue(11 in vec)
    }

    @Test
    fun testIndexOf() {
        val vec = Vec<Int>()
        vec.add(10)
        vec.add(11)
        vec.add(12)
        vec.add(1, 10)
        assertTrue(vec.indexOf(30) == -1)
    }

    @Test
    fun testRemove() {
        val vec = Vec<Int>()
        vec.add(10)
        vec.add(30)
        vec.remove(element = 10)
        assertTrue(vec.indexOf(10) == -1)
        vec.remove(element = 30)
        assertTrue(vec.indexOf(30) == -1)
    }
}
