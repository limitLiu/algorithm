package tree

import ltd.rust_lang.utils.tree.AVLTree
import ltd.rust_lang.utils.tree.BST
import ltd.rust_lang.utils.tree.RBTree
import org.junit.Assert
import org.junit.Test

class BSTTest {
    @Test
    fun testBST() {
        val bst = BST<Int>()
        bst.add(10)
        bst.add(11)
        bst.add(12)
        Assert.assertTrue(10 in bst)
        val avl = AVLTree<Int>()
        avl.add(11)
        avl.add(12)
        avl.remove(11)
        avl.add(14)
        avl.add(15)
        Assert.assertTrue(11 !in avl)
        Assert.assertTrue(avl.isComplete)

        val rbt = RBTree<Int>()
        rbt.add(11)
        rbt.add(12)
        rbt.remove(11)
        Assert.assertTrue(11 !in rbt)
        Assert.assertTrue(rbt.height == 1)
    }
}
