package map

import wiki.mdzz.utils.map.LinkedHashMap
import org.junit.Assert
import org.junit.Test

class LinkedHashMapTest {
    @Test
    fun testLinkedHashMap() {
        val hashMap = LinkedHashMap<String, Int>()
        Assert.assertTrue(hashMap.put("class", 2) == null)
        hashMap.put("text", 4)
        Assert.assertTrue(hashMap.put("text", 3) == 4)
        hashMap["text"] = 5
        Assert.assertTrue(hashMap.remove("text") == 5)
        Assert.assertTrue("text" !in hashMap)
        Assert.assertTrue(!hashMap.containsValue(5))
        Assert.assertTrue(hashMap["class"] == 2)
        Assert.assertTrue("class" in hashMap)
        Assert.assertTrue(hashMap.containsValue(2))
        Assert.assertTrue(hashMap.size() == 1)
    }
}
