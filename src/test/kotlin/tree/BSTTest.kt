package tree

import wiki.mdzz.utils.tree.AVLTree
import wiki.mdzz.utils.tree.BST
import wiki.mdzz.utils.tree.RBTree
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

    @Test
    fun travelTest() {
        val bst = BST<Int>()
        bst.add(7)
        bst.add(4)
        bst.add(9)
        bst.add(2)
        bst.add(5)
        bst.add(8)
        bst.add(11)
        val sb = StringBuilder()
        sb.delete(0, sb.length)
        bst.preorder { i ->
            sb.append(i).append(" ")
            return@preorder false
        }
        Assert.assertTrue(sb.toString() == "7 4 2 5 9 8 11 ")

        sb.delete(0, sb.length)
        bst.inorder { i ->
            sb.append(i).append(" ")
            return@inorder false
        }
        Assert.assertTrue(sb.toString() == "2 4 5 7 8 9 11 ")

        sb.delete(0, sb.length)
        bst.postorder { i ->
            sb.append(i).append(" ")
            return@postorder false
        }
        Assert.assertTrue(sb.toString() == "2 5 4 8 11 9 7 ")

        sb.delete(0, sb.length)
        bst.preOrder { i ->
            sb.append(i).append(" ")
            return@preOrder false
        }
        Assert.assertTrue(sb.toString() == "7 4 2 5 9 8 11 ")
    }
}
