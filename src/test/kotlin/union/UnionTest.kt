package union

import org.junit.Assert
import org.junit.Test
import net.wu_chinese.utils.union.UnionFind

class UnionTest {
    @Test
    fun testUnionFind() {
        val uf = UnionFind(12)
        uf.union(0, 1)
        uf.union(0, 3)
        uf.union(0, 4)
        uf.union(2, 3)
        uf.union(2, 5)

        uf.union(6, 7)

        uf.union(8, 10)
        uf.union(9, 10)
        uf.union(9, 11)

        Assert.assertFalse(uf.isSame(2, 7))
        uf.union(4, 6)
        Assert.assertTrue(uf.isSame(2, 7))
    }
}
