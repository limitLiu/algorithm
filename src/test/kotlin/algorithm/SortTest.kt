package algorithm

import org.junit.Assert
import org.junit.Test
import wiki.mdzz.algorithm.sort.*
import wiki.mdzz.utils.list.Vec

class SortTest {
    @Test
    fun bubbleTest() {
        val vec0 = Vec.arrayOf(35, 1, 3, 66, 5)
        Bubble<Int>().sort(vec0)
        Assert.assertTrue(vec0[0] == 1)
        Assert.assertTrue(vec0[1] == 3)
        Assert.assertTrue(vec0[2] == 5)
        Assert.assertTrue(vec0[3] == 35)
        Assert.assertTrue(vec0[4] == 66)

        val vec1 = Vec.arrayOf(35, 1, 3, 66, 5)
        val newVec1 = Bubble.sort2(vec1)
        Assert.assertTrue(newVec1[0] == 1)
        Assert.assertTrue(newVec1[1] == 3)
        Assert.assertTrue(newVec1[2] == 5)
        Assert.assertTrue(newVec1[3] == 35)
        Assert.assertTrue(newVec1[4] == 66)

        val vec2 = Vec.arrayOf(35, 1, 3, 66, 5)
        val newVec2 = Bubble.sort1(vec2)
        Assert.assertTrue(newVec2[0] == 1)
        Assert.assertTrue(newVec2[1] == 3)
        Assert.assertTrue(newVec2[2] == 5)
        Assert.assertTrue(newVec2[3] == 35)
        Assert.assertTrue(newVec2[4] == 66)
    }

    @Test
    fun selectionTest() {
        val vec0 = Vec.arrayOf(35, 1, 3, 66, 5)
        Selection<Int>().sort(vec0)
        Assert.assertTrue(vec0[0] == 1)
        Assert.assertTrue(vec0[1] == 3)
        Assert.assertTrue(vec0[2] == 5)
        Assert.assertTrue(vec0[3] == 35)
        Assert.assertTrue(vec0[4] == 66)
    }

    @Test
    fun heapTest() {
        val vec0 = Vec.arrayOf(35, 1, 3, 66, 5)
        Heap<Int>().sort(vec0)
        Assert.assertTrue(vec0[0] == 1)
        Assert.assertTrue(vec0[1] == 3)
        Assert.assertTrue(vec0[2] == 5)
        Assert.assertTrue(vec0[3] == 35)
        Assert.assertTrue(vec0[4] == 66)
    }

    @Test
    fun insertionTest() {
        val vec0 = Vec.arrayOf(35, 1, 3, 66, 5)
        Insertion<Int>().sort(vec0)
        Assert.assertTrue(vec0[0] == 1)
        Assert.assertTrue(vec0[1] == 3)
        Assert.assertTrue(vec0[2] == 5)
        Assert.assertTrue(vec0[3] == 35)
        Assert.assertTrue(vec0[4] == 66)

        Assert.assertTrue(BinarySearch.getInsertIndex(vec0, 3) == 2)
        Assert.assertTrue(BinarySearch.indexOf(vec0, 3) == 1)
    }
}