package wiki.mdzz.utils.tree

open class BSTExt<E>(comparator: Comparator<E>? = null) : BST<E>(comparator) {
    protected fun rotateLeft(grandpa: Node<E>?) {
        val parent = grandpa!!.right
        val child = parent!!.left
        grandpa.right = child
        parent.left = grandpa

        afterRotate(grandpa, parent, child)
    }

    protected open fun afterRotate(grandpa: Node<E>, parent: Node<E>, child: Node<E>?) {
        parent.parent = grandpa.parent
        when {
            grandpa.isLeftChild() -> {
                grandpa.parent!!.left = parent
            }
            grandpa.isRightChild() -> {
                grandpa.parent!!.right = parent
            }
            else -> {
                root = parent
            }
        }
        if (child != null) {
            child.parent = grandpa
        }
        grandpa.parent = parent
//        updateHeight(grandpa)
//        updateHeight(parent)
    }

    protected fun rotateRight(grandpa: Node<E>?) {
        val parent = grandpa!!.left
        val child = parent!!.right
        grandpa.left = child
        parent.right = grandpa

        afterRotate(grandpa, parent, child)
    }

    protected  open fun rotate(
        r: Node<E>,
        a: Node<E>?, b: Node<E>, c: Node<E>?,
        d: Node<E>,
        e: Node<E>?, f: Node<E>, g: Node<E>?
    ) {
        // make d be root
        d.parent = r.parent
        when {
            r.isLeftChild() -> {
                r.parent?.left = d
            }
            r.isRightChild() -> {
                r.parent?.right = d
            }
            else -> {
                root = d
            }
        }

        // a-b-c
        b.left = a
        if (a != null) {
            a.parent = b
        }

        b.right = c
        if (c != null) {
            c.parent = b
        }
        // e-f-g
        f.left = e
        if (e != null) {
            e.parent = f
        }

        f.right = g
        if (g != null) {
            g.parent = f
        }

        d.left = b
        d.right = f
        b.parent = d
        f.parent = d
    }

}
