package map

import ltd.rust_lang.utils.map.TreeMap
import org.junit.Assert
import org.junit.Test

class TreeMapTest {
    @Test
    fun testTreeMap() {
        val treeMap = TreeMap<String, Int>()
        Assert.assertTrue(treeMap.put("class", 2) == null)
        treeMap.put("text", 4)
        Assert.assertTrue(treeMap.put("text", 3) == 4)
        treeMap["text"] = 5
        Assert.assertTrue(treeMap.remove("text") == 5)
        Assert.assertTrue(treeMap["class"] == 2)
        Assert.assertTrue("class" in treeMap)
        Assert.assertTrue(treeMap.containsValue(2))
        Assert.assertTrue(treeMap.size() == 1)
    }
}
