package tree

import org.junit.Assert
import org.junit.Test
import wiki.mdzz.utils.tree.Trie

class TrieTest {
    @Test
    fun trieTest() {
        val trie = Trie<Int>()
        trie.add("cat", 1)
        trie.add("dog", 2)
        trie.add("catalog", 3)
        trie.add("cast", 4)
        trie.add("中文", 5)
        Assert.assertTrue(trie.size() == 5)
        Assert.assertTrue(!trie.isEmpty)
        Assert.assertTrue(trie.startsWith("中"))
        Assert.assertTrue(trie["中文"] == 5)
        Assert.assertTrue("cast" in trie)
        Assert.assertTrue(trie.remove("中文") == 5)
        Assert.assertTrue(trie.remove("cat") == 1)
        Assert.assertTrue(trie.size() == 3)
        Assert.assertTrue(trie.startsWith("do"))
        Assert.assertTrue(!trie.startsWith("中"))
        Assert.assertTrue(trie.startsWith("cat"))
    }
}
