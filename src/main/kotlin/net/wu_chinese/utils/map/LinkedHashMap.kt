package net.wu_chinese.utils.map

import java.util.Objects

class LinkedHashMap<K, V> : HashMap<K, V>() {
    private var head: LinkedNode<K, V>? = null
    private var tail: LinkedNode<K, V>? = null

    override fun clear() {
        super.clear()
        head = null
        tail = null
    }

    override fun traversal(visitor: ((k: K?, v: V?) -> Boolean)?) {
        if (visitor == null) return
        var node = head
        while (node != null) {
            if (visitor.invoke(node.key, node.value)) {
                return
            }
            node = node.next
        }
    }

    override fun createNode(key: K, value: V, parent: Node<K, V>?): Node<K, V> {
        val node = LinkedNode(key, value, parent)
        if (head == null) {
            tail = node
            head = tail
        } else {
            tail?.next = node
            node.prev = tail
            tail = node
        }
        return node
    }

    inner class LinkedNode<K, V>(key: K, value: V, parent: Node<K, V>?) :
        Node<K, V>(key, value, parent) {
        var prev: LinkedNode<K, V>? = null
        var next: LinkedNode<K, V>? = null
    }

    override fun containsValue(value: V): Boolean {
        var node = head
        while (node != null) {
            if (Objects.equals(value, node.value)) return true
            node = node.next
        }
        return false
    }

    override fun afterRemove(willRemove: Node<K, V>, shouldRemove: Node<K, V>) {
        val should: LinkedNode<K, V> = shouldRemove as LinkedNode<K, V>
        if (willRemove != shouldRemove) {
            val will: LinkedNode<K, V> = willRemove as LinkedNode<K, V>
            var temp = will.prev
            will.prev = should.prev
            should.prev = temp
            if (will.prev == null) {
                head = will
            } else {
                will.prev!!.next = will
            }
            if (should.prev == null) {
                head = should
            } else {
                should.prev!!.next = should
            }


            temp = will.next
            will.next = should.next
            should.next = temp
            if (will.next == null) {
                tail = will
            } else {
                will.next!!.prev = will
            }

            if (should.next == null) {
                tail = should
            } else {
                should.next!!.prev = should
            }
        }

        val next: LinkedNode<K, V>? = should.next
        val prev: LinkedNode<K, V>? = should.prev
        if (prev == null) {
            head = next
        } else {
            prev.next = next
        }

        if (next == null) {
            tail = prev
        } else {
            next.prev = prev
        }
    }

}
