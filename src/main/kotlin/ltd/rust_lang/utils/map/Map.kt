package ltd.rust_lang.utils.map

interface Map<K, V> {
    fun size(): Int
    val isEmpty: Boolean
    fun clear()
    fun put(key: K, value: V): V?
    operator fun set(key: K, value: V)
    operator fun get(key: K): V?
    fun remove(key: K?): V?
    operator fun contains(key: K?): Boolean
    fun containsValue(value: V): Boolean
    fun traversal(visitor: ((k: K?, v: V?) -> Boolean)?)
}
