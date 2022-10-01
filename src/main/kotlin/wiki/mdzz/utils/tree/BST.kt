package wiki.mdzz.utils.tree

open class BST<E>(private val comparator: Comparator<E>? = null) : BinaryTree<E>() {
    fun add(e: E) {
        elementNotNullCheck(e)
        if (root == null) {
            root = createNode(e, null)
            afterAdd(root!!)
        } else {
            var parent: Node<E>?
            var node = root
            var cmp: Int
            do {
                parent = node
                cmp = compare(e, node!!.element)
                node = when {
                    cmp > 0 -> {
                        node.right
                    }
                    cmp < 0 -> {
                        node.left
                    }
                    else -> {
                        node.element = e
                        return
                    }
                }
            } while (node != null)

            val newNode = createNode(e, parent)
            if (cmp > 0) {
                parent!!.right = newNode
            } else if (cmp < 0) {
                parent!!.left = newNode
            }
            afterAdd(newNode)
        }
        size++
    }

    protected open fun afterAdd(tmp: Node<E>?) {}
    protected open fun afterRemove(tmp: Node<E>?) {}

    protected open fun createNode(e: E, parent: Node<E>?): Node<E> {
        return Node(e, parent)
    }

    private fun compare(e1: E, e2: E): Int {
        if (comparator != null) {
            return comparator.compare(e1, e2)
        }
        return (e1 as Comparable<E>).compareTo(e2)
    }

    fun remove(e: E) {
        remove(getNode(e))
    }

    private fun remove(node: Node<E>?) {
        var current: Node<E>? = node ?: return
        // 子树(度) 为 2
        if (current!!.hasTwoChildren()) {
            val s = successor(node)
            current.element = s!!.element
            current = s
        }
        val replacement = if (current.left != null) {
            current.left
        } else {
            current.right
        }
        when {
            replacement != null -> {
                replacement.parent = current.parent
                when {
                    current.parent == null -> {
                        root = replacement
                    }
                    current == current.parent!!.left -> {
                        current.parent!!.left = replacement
                    }
                    else -> {
                        current.parent!!.right = replacement
                    }
                }
                afterRemove(replacement)
            }
            current.parent == null -> { // 根节点
                root = null
                afterRemove(current)
            }
            else -> { // 不为根节点的叶子
                if (current == current.parent!!.right) {
                    current.parent!!.right = null
                } else {
                    current.parent!!.left = null
                }
                afterRemove(current)
            }
        }
        size--
    }

    private fun getNode(e: E): Node<E>? {
        var tmp = root
        while (tmp != null) {
            val cmp = compare(e, tmp.element)
            tmp = when {
                cmp > 0 -> tmp.right
                cmp < 0 -> tmp.left
                else -> return tmp
            }
        }
        return null
    }

    operator fun contains(e: E): Boolean {
        return getNode(e) != null
    }

    private fun elementNotNullCheck(e: E?) {
        if (e == null) {
            throw IllegalArgumentException("element must not be null")
        }
    }
}
