package algorithm

import org.junit.Assert
import org.junit.Test
import wiki.mdzz.algorithm.Bubble
import wiki.mdzz.utils.list.Vec

class SortTest {
    @Test
    fun bubbleTest() {
        val vec0 = Vec.arrayOf(35, 1, 3, 66, 5)
        val newVec0 = Bubble.sort1(vec0)
        Assert.assertTrue(newVec0[0] == 1)
        Assert.assertTrue(newVec0[1] == 3)
        Assert.assertTrue(newVec0[2] == 5)
        Assert.assertTrue(newVec0[3] == 35)
        Assert.assertTrue(newVec0[4] == 66)

        val vec1 = Vec.arrayOf(35, 1, 3, 66, 5)
        val newVec1 = Bubble.sort2(vec1)
        Assert.assertTrue(newVec1[0] == 1)
        Assert.assertTrue(newVec1[1] == 3)
        Assert.assertTrue(newVec1[2] == 5)
        Assert.assertTrue(newVec1[3] == 35)
        Assert.assertTrue(newVec1[4] == 66)

        val vec2 = Vec.arrayOf(35, 1, 3, 66, 5)
        val newVec2 = Bubble.sort3(vec2)
        Assert.assertTrue(newVec2[0] == 1)
        Assert.assertTrue(newVec2[1] == 3)
        Assert.assertTrue(newVec2[2] == 5)
        Assert.assertTrue(newVec2[3] == 35)
        Assert.assertTrue(newVec2[4] == 66)
    }
}