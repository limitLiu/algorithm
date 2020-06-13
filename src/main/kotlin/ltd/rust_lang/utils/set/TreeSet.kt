package ltd.rust_lang.utils.set

import ltd.rust_lang.utils.tree.RBTree

class TreeSet<E> : Set<E> {
    private val container = RBTree<E>()

    override fun size(): Int {
        return container.size()
    }

    override val isEmpty: Boolean
        get() = size() == 0

    override fun clear() {
        container.clear()
    }

    override fun contains(element: E?): Boolean {
        if (element != null) {
            return container.contains(element)
        }
        return false
    }

    override fun add(element: E?) {
        if (element != null) {
            container.add(element)
        }
    }

    override fun remove(element: E?) {
        if (element != null) {
            container.remove(element)
        }
    }

    override fun traversal(visitor: ((e: E?) -> Boolean)?) {
        container.inorder(visitor)
    }
}
