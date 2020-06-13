package ltd.rust_lang.utils.map;

import kotlin.jvm.functions.Function2;
import ltd.rust_lang.utils.queue.Queue;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Objects;

public class HashMap<K, V> implements Map<K, V> {
    private static final boolean RED = false;
    private static final boolean BLACK = true;
    private int size;
    private Node<K, V>[] table;
    private static final int DEFAULT_CAPACITY = 1 << 4;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    public HashMap() {
        table = new Node[DEFAULT_CAPACITY];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        if (size == 0) return;
        size = 0;
        Arrays.fill(table, null);
    }

    @Override
    public V put(K key, V value) {
        resize();

        int index = index(key);
        Node<K, V> root = table[index];
        if (root == null) {
            root = createNode(key, value, null);
            table[index] = root;
            size++;
            fixAfterPut(root);
            return null;
        }

        Node<K, V> parent = root;
        Node<K, V> node = root;
        int cmp;
        int h1 = hash(key);
        Node<K, V> result;
        boolean searched = false;
        do {
            parent = node;
            K k2 = node.key;
            int h2 = node.hash;
            if (h1 > h2) {
                cmp = 1;
            } else if (h1 < h2) {
                cmp = -1;
            } else if (Objects.equals(key, k2)) {
                cmp = 0;
            } else if (k2 != null
                    && key instanceof Comparable
                    && key.getClass() == k2.getClass()
                    && (cmp = ((Comparable) key).compareTo(k2)) != 0) {
            } else if (searched) {
                cmp = System.identityHashCode(key) - System.identityHashCode(k2);
            } else {
                if ((node.left != null && (result = node(node.left, key)) != null)
                        || (node.right != null && (result = node(node.right, key)) != null)) {
                    node = result;
                    cmp = 0;
                } else {
                    searched = true;
                    cmp = System.identityHashCode(key) - System.identityHashCode(k2);
                }
            }

            if (cmp > 0) {
                node = node.right;
            } else if (cmp < 0) {
                node = node.left;
            } else {
                V oldValue = node.value;
                node.key = key;
                node.value = value;
                node.hash = h1;
                return oldValue;
            }
        } while (node != null);

        Node<K, V> newNode = createNode(key, value, parent);
        if (cmp > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }
        size++;

        fixAfterPut(newNode);
        return null;
    }

    @Override
    public V get(K key) {
        Node<K, V> node = node(key);
        return node != null ? node.value : null;
    }

    @Override
    public V remove(K key) {
        return remove(node(key));
    }

    @Override
    public boolean containsValue(V value) {
        if (size == 0) return false;
        Queue<Node<K, V>> queue = new Queue<>();
        for (Node<K, V> kvNode : table) {
            if (kvNode == null) continue;
            queue.offer(kvNode);
            while (!queue.isEmpty()) {
                Node<K, V> node = queue.poll();
                assert node != null;
                if (Objects.equals(value, node.value)) return true;

                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }
        return false;
    }

    protected Node<K, V> createNode(K key, V value, Node<K, V> parent) {
        return new Node<>(key, value, parent);
    }

    protected void afterRemove(Node<K, V> willNode, Node<K, V> removedNode) {
    }

    private void resize() {
        // 装填因子 <= 0.75
        if ((float) (size / table.length) <= DEFAULT_LOAD_FACTOR) return;

        Node<K, V>[] oldTable = table;
        table = new Node[oldTable.length << 1];

        Queue<Node<K, V>> queue = new Queue<>();
        for (Node<K, V> kvNode : oldTable) {
            if (kvNode == null) continue;

            queue.offer(kvNode);
            while (!queue.isEmpty()) {
                Node<K, V> node = queue.poll();
                assert node != null;
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }

                moveNode(node);
            }
        }
    }

    private void moveNode(Node<K, V> newNode) {
        newNode.parent = null;
        newNode.left = null;
        newNode.right = null;
        newNode.color = RED;

        int index = index(newNode);
        Node<K, V> root = table[index];
        if (root == null) {
            root = newNode;
            table[index] = root;
            fixAfterPut(root);
            return;
        }

        Node<K, V> parent = root;
        Node<K, V> node = root;
        int cmp = 0;
        K k1 = newNode.key;
        int h1 = newNode.hash;
        do {
            parent = node;
            K k2 = node.key;
            int h2 = node.hash;
            if (h1 > h2) {
                cmp = 1;
            } else if (h1 < h2) {
                cmp = -1;
            } else if (k2 != null
                    && k1 instanceof Comparable
                    && k1.getClass() == k2.getClass()
                    && (cmp = ((Comparable) k1).compareTo(k2)) != 0) {
            } else {
                cmp = System.identityHashCode(k1) - System.identityHashCode(k2);
            }

            if (cmp > 0) {
                node = node.right;
            } else if (cmp < 0) {
                node = node.left;
            }
        } while (node != null);

        // 看看插入到父节点的哪个位置
        newNode.parent = parent;
        if (cmp > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }

        // 新添加节点之后的处理
        fixAfterPut(newNode);
    }

    protected V remove(Node<K, V> node) {
        if (node == null) return null;

        Node<K, V> willNode = node;

        size--;

        V oldValue = node.value;

        if (node.hasTwoChildren()) { // 度为2的节点
            // 找到后继节点
            Node<K, V> s = successor(node);
            // 用后继节点的值覆盖度为2的节点的值
            node.key = s.key;
            node.value = s.value;
            node.hash = s.hash;
            // 删除后继节点
            node = s;
        }

        // 删除node节点（node的度必然是1或者0）
        Node<K, V> replacement = node.left != null ? node.left : node.right;
        int index = index(node);

        if (replacement != null) { // node是度为1的节点
            // 更改parent
            replacement.parent = node.parent;
            // 更改parent的left、right的指向
            if (node.parent == null) { // node是度为1的节点并且是根节点
                table[index] = replacement;
            } else if (node == node.parent.left) {
                node.parent.left = replacement;
            } else { // node == node.parent.right
                node.parent.right = replacement;
            }

            // 删除节点之后的处理
            fixAfterRemove(replacement);
        } else if (node.parent == null) { // node是叶子节点并且是根节点
            table[index] = null;
        } else { // node是叶子节点，但不是根节点
            if (node == node.parent.left) {
                node.parent.left = null;
            } else { // node == node.parent.right
                node.parent.right = null;
            }

            // 删除节点之后的处理
            fixAfterRemove(node);
        }

        // 交给子类去处理
        afterRemove(willNode, node);

        return oldValue;
    }

    private Node<K, V> successor(Node<K, V> node) {
        if (node == null) return null;

        // 前驱节点在左子树当中（right.left.left.left....）
        Node<K, V> p = node.right;
        if (p != null) {
            while (p.left != null) {
                p = p.left;
            }
            return p;
        }

        // 从父节点、祖父节点中寻找前驱节点
        while (node.parent != null && node == node.parent.right) {
            node = node.parent;
        }

        return node.parent;
    }

    private Node<K, V> node(K key) {
        Node<K, V> root = table[index(key)];
        return root == null ? null : node(root, key);
    }

    private Node<K, V> node(Node<K, V> node, K k1) {
        int h1 = hash(k1);
        Node<K, V> result;
        int cmp;
        while (node != null) {
            K k2 = node.key;
            int h2 = node.hash;
            // 先比较哈希值
            if (h1 > h2) {
                node = node.right;
            } else if (h1 < h2) {
                node = node.left;
            } else if (Objects.equals(k1, k2)) {
                return node;
            } else if (k2 != null
                    && k1 instanceof Comparable
                    && k1.getClass() == k2.getClass()
                    && (cmp = ((Comparable) k1).compareTo(k2)) != 0) {
                node = cmp > 0 ? node.right : node.left;
            } else if (node.right != null && (result = node(node.right, k1)) != null) {
                return result;
            } else {
                node = node.left;
            }
        }
        return null;
    }

    private int index(K key) {
        return hash(key) & (table.length - 1);
    }

    private int hash(K key) {
        if (key == null) return 0;
        int hash = key.hashCode();
        return hash ^ (hash >>> 16);
    }

    private int index(Node<K, V> node) {
        return node.hash & (table.length - 1);
    }

    private void fixAfterRemove(Node<K, V> node) {
        // 如果删除的节点是红色
        // 或者 用以取代删除节点的子节点是红色
        if (isRed(node)) {
            setBlack(node);
            return;
        }

        Node<K, V> parent = node.parent;
        if (parent == null) return;

        boolean left = parent.left == null || node.isLeftChild();
        Node<K, V> sibling = left ? parent.right : parent.left;
        if (left) {
            if (isRed(sibling)) {
                setBlack(sibling);
                setRed(parent);
                rotateLeft(parent);
                sibling = parent.right;
            }

            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                boolean parentBlack = isBlack(parent);
                setBlack(parent);
                setRed(sibling);
                if (parentBlack) {
                    fixAfterRemove(parent);
                }
            } else {
                if (isBlack(sibling.right)) {
                    rotateRight(sibling);
                    sibling = parent.right;
                }

                color(sibling, colorOf(parent));
                setBlack(sibling.right);
                setBlack(parent);
                rotateLeft(parent);
            }
        } else {
            if (isRed(sibling)) {
                setBlack(sibling);
                setRed(parent);
                rotateRight(parent);
                sibling = parent.left;
            }

            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                boolean parentBlack = isBlack(parent);
                setBlack(parent);
                setRed(sibling);
                if (parentBlack) {
                    fixAfterRemove(parent);
                }
            } else {
                if (isBlack(sibling.left)) {
                    rotateLeft(sibling);
                    sibling = parent.left;
                }

                color(sibling, colorOf(parent));
                setBlack(sibling.left);
                setBlack(parent);
                rotateRight(parent);
            }
        }
    }

    private void fixAfterPut(Node<K, V> node) {
        Node<K, V> parent = node.parent;

        if (parent == null) {
            setBlack(node);
            return;
        }

        if (isBlack(parent)) return;

        Node<K, V> uncle = parent.sibling();
        Node<K, V> grand = setRed(parent.parent);
        if (isRed(uncle)) {
            setBlack(parent);
            setBlack(uncle);
            fixAfterPut(grand);
            return;
        }

        if (parent.isLeftChild()) { // L
            if (node.isLeftChild()) { // LL
                setBlack(parent);
            } else { // LR
                setBlack(node);
                rotateLeft(parent);
            }
            rotateRight(grand);
        } else { // R
            if (node.isLeftChild()) { // RL
                setBlack(node);
                rotateRight(parent);
            } else { // RR
                setBlack(parent);
            }
            rotateLeft(grand);
        }
    }

    private void rotateLeft(Node<K, V> grand) {
        Node<K, V> parent = grand.right;
        Node<K, V> child = parent.left;
        grand.right = child;
        parent.left = grand;
        afterRotate(grand, parent, child);
    }

    private void rotateRight(Node<K, V> grand) {
        Node<K, V> parent = grand.left;
        Node<K, V> child = parent.right;
        grand.left = child;
        parent.right = grand;
        afterRotate(grand, parent, child);
    }

    private void afterRotate(Node<K, V> grand, Node<K, V> parent, Node<K, V> child) {
        parent.parent = grand.parent;
        if (grand.isLeftChild()) {
            grand.parent.left = parent;
        } else if (grand.isRightChild()) {
            grand.parent.right = parent;
        } else {
            table[index(grand)] = parent;
        }

        if (child != null) {
            child.parent = grand;
        }

        grand.parent = parent;
    }

    private Node<K, V> color(Node<K, V> node, boolean color) {
        if (node == null) return null;
        node.color = color;
        return node;
    }

    private Node<K, V> setRed(Node<K, V> node) {
        return color(node, RED);
    }

    private Node<K, V> setBlack(Node<K, V> node) {
        return color(node, BLACK);
    }

    private boolean colorOf(Node<K, V> node) {
        return node == null ? BLACK : node.color;
    }

    private boolean isBlack(Node<K, V> node) {
        return colorOf(node) == BLACK;
    }

    private boolean isRed(Node<K, V> node) {
        return colorOf(node) == RED;
    }

    @Override
    public void set(K key, V value) {
        put(key, value);
    }

    @Override
    public boolean contains(@Nullable K key) {
        return node(key) != null;
    }

    @Override
    public void traversal(@Nullable Function2<? super K, ? super V, Boolean> visitor) {
        Queue<Node<K, V>> queue = new Queue<>();
        for (Node<K, V> kvNode : table) {
            if (kvNode == null) continue;
            queue.offer(kvNode);
            while (!queue.isEmpty()) {
                Node<K, V> node = queue.poll();
                assert visitor != null;
                assert node != null;
                if (visitor.invoke(node.key, node.value)) return;

                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }
    }

    protected static class Node<K, V> {
        int hash;
        K key;
        V value;
        boolean color = RED;
        Node<K, V> left;
        Node<K, V> right;
        Node<K, V> parent;

        public Node(K key, V value, Node<K, V> parent) {
            this.key = key;
            int hash = key == null ? 0 : key.hashCode();
            this.hash = hash ^ (hash >>> 16);
            this.value = value;
            this.parent = parent;
        }

        public boolean hasTwoChildren() {
            return left != null && right != null;
        }

        public boolean isLeftChild() {
            return parent != null && this == parent.left;
        }

        public boolean isRightChild() {
            return parent != null && this == parent.right;
        }

        public Node<K, V> sibling() {
            if (isLeftChild()) {
                return parent.right;
            }

            if (isRightChild()) {
                return parent.left;
            }
            return null;
        }

        @Override
        public String toString() {
            return "Node_" + key + "_" + value;
        }
    }
}
