package set

import net.wu_chinese.utils.set.ListSet
import net.wu_chinese.utils.set.TreeSet
import net.wu_chinese.utils.set.HashSet
import net.wu_chinese.utils.set.LinkedHashSet
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

    @Test
    fun testHashSet() {
        val set = HashSet<Int?>()
        set.add(10)
        set.add(10)
        set.add(null)
        Assert.assertTrue(set.size() == 1)
        set.remove(10)
        Assert.assertTrue(set.isEmpty)
    }

    @Test
    fun testLinkedHashSet() {
        val set = LinkedHashSet<Int?>()
        set.add(10)
        set.add(10)
        set.add(null)
        Assert.assertTrue(set.size() == 1)
        set.remove(10)
        Assert.assertTrue(set.isEmpty)
    }
}
