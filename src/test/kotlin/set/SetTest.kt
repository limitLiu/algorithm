package set

import wiki.mdzz.utils.set.ListSet
import wiki.mdzz.utils.set.TreeSet
import org.junit.Assert
import org.junit.Test

class SetTest {
    @Test
    fun testListSet() {
        val set = ListSet<Int?>()
        set.add(10)
        set.add(10)
        set.add(null)
        Assert.assertTrue(set.size() == 2)
        set.remove(10)
        set.remove(null)
        Assert.assertTrue(set.isEmpty)
    }

    @Test
    fun testTreeSet() {
        val set = TreeSet<Int?>()
        set.add(10)
        set.add(10)
        set.add(null)
        Assert.assertTrue(set.size() == 1)
        set.remove(10)
        Assert.assertTrue(set.isEmpty)
    }
}
