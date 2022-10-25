package algorithm

import org.junit.Assert
import org.junit.Test
import wiki.mdzz.algorithm.Bubble
import wiki.mdzz.utils.list.Vec

class SortTest {
    @Test
    fun bubbleTest() {
        val vec = Vec.arrayOf(35, 1, 3, 66, 5)
        val newVec = Bubble.sort(vec)
        Assert.assertTrue(newVec[0] == 1)
        Assert.assertTrue(newVec[1] == 3)
        Assert.assertTrue(newVec[2] == 5)
        Assert.assertTrue(newVec[3] == 35)
        Assert.assertTrue(newVec[4] == 66)
    }
}