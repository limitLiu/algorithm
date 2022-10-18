package wiki.mdzz.utils.set


class HashSet<E> : Set<E> {
    private val container = wiki.mdzz.utils.map.HashMap<E, Unit?>()

    override val isEmpty: Boolean
        get() = size() == 0

    override fun size(): Int {
        return container.size()
    }

    override fun clear() {
        container.clear()
    }

    override fun contains(element: E?): Boolean {
        return container.contains(element)
    }

    override fun add(element: E?) {
        if (element != null) {
            container.put(element, null)
        }
    }

    override fun remove(element: E?) {
        container.remove(element)
    }

    override fun traversal(visitor: ((e: E?) -> Boolean)?) {
        if (visitor != null) {
            container.traversal { k, _: Unit? ->
                visitor.invoke(k)
            }
        }
    }
}
