import net.wu_chinese.entities.Person
import net.wu_chinese.utils.map.LinkedHashMap

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val hashMap = LinkedHashMap<Person, Int>()
        var p1: Person? = null
        var p2: Person? = null
        for (i in 0 until 21) {
            when (i) {
                11 -> {
                    p1 = Person(i, "p${i}")
                    hashMap.put(p1, i)
                }

                12 -> {
                    p2 = Person(i, "p${i}")
                    hashMap.put(p2, i)
                }

                else -> {
                    val p = Person(i, "p${i}")
                    hashMap.put(p, i)
                }
            }
        }

        hashMap.remove(p1)
        hashMap.remove(p2)
        println("contains: ${hashMap.contains(p2)}")
        println("containsValue: ${hashMap.containsValue(12)}")
        hashMap.traversal { k, v ->
            println("key: $k\tvalue: $v")
            return@traversal false
        }
    }
}
