package net.wu_chinese.utils.tree

import kotlin.math.abs
import kotlin.math.max

class AVLTree<E>(comparator: Comparator<E>? = null) : BSTExt<E>(comparator) {
    private inner class AVLNode<E>(e: E, parent: Node<E>?) : Node<E>(e, parent) {
        var height = 1
        fun getH(): Pair<Int, Int> {
            val leftH = if (left == null) {
                0
            } else {
                (left as AVLNode<E>).height
            }
            val rightH = if (right == null) {
                0
            } else {
                (right as AVLNode<E>).height
            }
            return Pair(leftH, rightH)
        }

        fun updateH() {
            val (leftH, rightH) = this.getH()
            height = 1 + max(leftH, rightH)
        }

        fun taller(): Node<E>? {
            val (leftH, rightH) = getH()
            if (leftH > rightH) return left
            if (leftH < rightH) return right
            return if (isLeftChild()) {
                left
            } else {
                right
            }
        }

        fun balanceFactor(): Int {
            val (leftH, rightH) = getH()
            return leftH - rightH
        }

        override fun toString(): String {
            var parentString = "null"
            if (parent != null) {
                parentString = parent!!.element.toString()
            }
            return element.toString() + "_p(" + parentString + ")_h(" + height + ")"
        }
    }

    private fun updateHeight(node: Node<E>?) {
        (node as AVLNode<E>).updateH()
    }

    private fun isBalance(node: Node<E>?): Boolean {
        return abs((node as AVLNode<E>).balanceFactor()) <= 1
    }

    override fun createNode(e: E, parent: Node<E>?): Node<E> {
        return AVLNode(e, parent)
    }

    /*
    private fun reBalance(tmp: Node<E>?) {
        val g = tmp ?: return
        val p = (g as AVLNode<E>).taller() as AVLNode<E>
        val n = p.taller() as AVLNode<E>

        if (p.isLeftChild()) { // L
            if (n.isLeftChild()) { // LL
                rotateRight(g)
            } else { // LR
                rotateLeft(p)
                rotateRight(g)
            }
        } else { // R
            if (n.isLeftChild()) { // RR
                rotateRight(p)
                rotateLeft(g)
            } else { // RL
                rotateLeft(g)
            }
        }
    }
     */
    private fun reBalance(tmp: Node<E>?) {
        val grandpa = tmp ?: return
        val parent = (grandpa as AVLNode<E>).taller() as AVLNode<E>
        val node = parent.taller() as AVLNode<E>

        when (Pair(parent.isLeftChild(), node.isLeftChild())) {
            Pair(first = true, second = true) ->
                rotate(
                    grandpa,
                    node.left,
                    node,
                    node.right,
                    parent,
                    parent.right,
                    grandpa,
                    grandpa.right
                )

            Pair(first = true, second = false) ->
                rotate(
                    grandpa,
                    parent.left,
                    parent,
                    node.right,
                    node,
                    node.right,
                    grandpa,
                    grandpa.right
                )

            Pair(first = false, second = true) ->
                rotate(
                    grandpa,
                    grandpa.left,
                    grandpa,
                    node.left,
                    node,
                    node.right,
                    parent,
                    parent.right
                )

            Pair(first = false, second = false) ->
                rotate(
                    grandpa,
                    grandpa.left,
                    grandpa,
                    parent.left,
                    parent,
                    node.left,
                    node,
                    node.right
                )
        }
    }

    override fun afterRemove(tmp: Node<E>?) {
        var node = tmp ?: return
        while (node.parent?.also { node = it } != null) {
            if (isBalance(node)) {
                updateHeight(node)
            } else {
                reBalance(node)
            }
        }
    }

    override fun afterAdd(tmp: Node<E>?) {
        var node = tmp ?: return
        while (node.parent?.also { node = it } != null) {
            if (isBalance(node)) {
                updateHeight(node)
            } else {
                reBalance(node)
                break
            }
        }
    }

    override fun afterRotate(grandpa: Node<E>, parent: Node<E>, child: Node<E>?) {
        super.afterRotate(grandpa, parent, child)
        updateHeight(grandpa)
        updateHeight(parent)
    }

    override fun rotate(
        r: Node<E>,
        a: Node<E>?,
        b: Node<E>,
        c: Node<E>?,
        d: Node<E>,
        e: Node<E>?,
        f: Node<E>,
        g: Node<E>?
    ) {
        super.rotate(r, a, b, c, d, e, f, g)
        updateHeight(b)
        updateHeight(f)
        updateHeight(d)
    }

}
