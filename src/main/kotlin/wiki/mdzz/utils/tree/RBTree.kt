package wiki.mdzz.utils.tree

class RBTree<E>(comparator: Comparator<E>? = null) : BSTExt<E>(comparator) {
    companion object {
        const val RED = false
        const val BLACK = true
    }

    override fun createNode(e: E, parent: Node<E>?): Node<E> {
        return RBNode(e, parent)
    }

    private fun setColor(color: Boolean, node: Node<E>?): Node<E>? {
        if (node != null) {
            (node as RBNode<E>).color = color
        }
        return node
    }

    private fun setRed(node: Node<E>?): Node<E>? {
        return setColor(RED, node)
    }

    private fun setBlack(node: Node<E>?): Node<E>? {
        return setColor(BLACK, node)
    }

    private fun colorOf(node: Node<E>?): Boolean {
        return (node as RBNode<E>?)?.color ?: BLACK
    }

    private fun isBlack(node: Node<E>?): Boolean {
        return colorOf(node) == BLACK
    }

    private fun isRed(node: Node<E>?): Boolean {
        return colorOf(node) == RED
    }

    private inner class RBNode<E>(e: E, parent: Node<E>?) : Node<E>(e, parent) {
        var color: Boolean = RED

        override fun toString(): String {
            var str = ""
            if (color == RED) {
                str = "R_"
            }
            return str + element.toString()
        }
    }

    override fun afterAdd(tmp: Node<E>?) {
        val parent = tmp?.parent
        if (parent == null) {
            setBlack(tmp)
            return
        }

        if (isBlack(parent)) return

        val uncle = parent.sibling()
        val grandpa = setRed(parent.parent)
        if (isRed(uncle)) {
            setBlack(parent)
            setBlack(uncle)
            afterAdd(grandpa)
            return
        }
        when (Pair(parent.isLeftChild(), tmp.isLeftChild())) {
            Pair(first = true, second = true) -> {
                // LL
                setBlack(parent)
                rotateRight(grandpa)
            }
            Pair(first = true, second = false) -> {
                // LR
                setBlack(tmp)
                rotateLeft(parent)
                rotateRight(grandpa)
            }
            Pair(first = false, second = true) -> {
                // RL
                setBlack(tmp)
                rotateRight(parent)
                rotateLeft(grandpa)
            }
            Pair(first = false, second = false) -> {
                // RR
                setBlack(parent)
                rotateLeft(grandpa)
            }
        }
    }

    override fun afterRemove(tmp: Node<E>?) {
        if (isRed(tmp)) {
            setBlack(tmp)
            return
        }
        val parent = tmp!!.parent ?: return
        val isLeft = parent.left == null || tmp.isLeftChild()
        var sibling = if (isLeft) {
            parent.right
        } else {
            parent.left
        }
        if (isLeft) {
            if (isRed(sibling)) {
                setBlack(sibling)
                setRed(parent)
                rotateLeft(parent)
                sibling = parent.right
            }
            if (isBlack(sibling!!.left) && isBlack(sibling.right)) {
                val isParentBlack = isBlack(parent)
                setBlack(parent)
                setRed(sibling)
                if (isParentBlack) {
                    afterRemove(parent)
                }
            } else {
                if (isBlack(sibling.right)) {
                    rotateRight(sibling)
                    sibling = parent.right
                }
                setColor(colorOf(parent), sibling)
                setBlack(sibling!!.right)
                setBlack(parent)
                rotateLeft(parent)
            }
        } else {
            if (isRed(sibling)) {
                setBlack(sibling)
                setRed(parent)
                rotateRight(parent)
                sibling = parent.left
            }
            if (isBlack(sibling!!.left) && isBlack(sibling.right)) {
                val isParentBlack = isBlack(parent)
                setBlack(parent)
                setRed(sibling)
                if (isParentBlack) {
                    afterRemove(parent)
                }
            } else {
                if (isBlack(sibling.left)) {
                    rotateLeft(sibling)
                    sibling = parent.left
                }
                setColor(colorOf(parent), sibling)
                setBlack(sibling!!.left)
                setBlack(parent)
                rotateRight(parent)
            }
        }
    }
}
