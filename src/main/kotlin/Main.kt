import wiki.mdzz.entities.Person
import wiki.mdzz.utils.map.HashMap

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val hashMap = HashMap<Person, Int>()
        val p1 = Person()
        val p2 = Person()
        hashMap.put(p1, 10)
        hashMap.put(p2, 11)
        hashMap.traversal { k, v ->
            println("key: $k\nvalue: $v")
            return@traversal false
        }
    }
}
