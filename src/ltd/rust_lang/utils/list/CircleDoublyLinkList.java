package ltd.rust_lang.utils.list;

public class CircleDoublyLinkList<E> extends DefaultList<E> {

  private Node<E> first;
  private Node<E> last;
  private Node<E> current;

  private static class Node<E> {
    E element;
    Node<E> prev;
    Node<E> next;

    Node(E element, Node<E> prev, Node<E> next) {
      this.element = element;
      this.next = next;
      this.prev = prev;
    }

    @Override
    public String toString() {
      StringBuilder str = new StringBuilder();
      if (prev != null) {
        str.append(prev.element);
      } else {
        str.append("null");
      }
      str.append("-").append(element).append("-");
      if (next != null) {
        str.append(next.element);
      } else {
        str.append("null");
      }
      return str.toString();
    }
  }

  @Override
  public void insert(int index, E element) {
    rangeCheckInsert(index);
    if (index == size()) {
      Node<E> old = last;
      last = new Node<>(element, old, first);
      if (old == null) {
        first = last;
        first.next = last;
      } else {
        old.next = last;
      }
      first.prev = last;
    } else {
      Node<E> next = getNode(index);
      Node<E> prev = next.prev;
      Node<E> newNode = new Node<>(element, prev, next);
      next.prev = newNode;
      prev.next = newNode;
      // index == 0
      if (next == first) {
        first = newNode;
      }
    }
    ++size;
  }

  @Override
  public E remove(int index) {
    rangeCheck(index);
    return remove(getNode(index));
  }

  @Override
  public E set(int index, E element) {
    Node<E> node = getNode(index);
    E old = node.element;
    node.element = element;
    return old;
  }

  @Override
  public E get(int index) {
    return getNode(index).element;
  }

  @Override
  public void clear() {
    size = 0;
    first = null;
    last = null;
  }

  @Override
  public boolean contains(E element) {
    return indexOf(element) != ELEMENT_NOT_FOUND;
  }

  @Override
  public int indexOf(E element) {
    Node<E> node = first;
    for (int i = 0; i < size(); ++i) {
      if (element.equals(node.element)) return i;
      node = node.next;
    }
    return ELEMENT_NOT_FOUND;
  }

  @Override
  public E remove(E element) {
    return remove(indexOf(element));
  }

  private E remove(Node<E> node) {
    if (size() == 1) {
      first = null;
      last = null;
    } else {
      Node<E> next = node.next;
      Node<E> prev = node.prev;
      prev.next = next;
      next.prev = prev;
      if (node == first) {
        first = next;
      }
      if (node == last) {
        last = prev;
      }
    }
    --size;
    return node.element;
  }

  private Node<E> getNode(int index) {
    rangeCheck(index);
    boolean tmp = index < (size() >> 1);
    Node<E> node = tmp ? first : last;
    if (tmp) {
      for (int i = 0; i < index; ++i) {
        node = node.next;
      }
    } else {
      for (int i = size(); i > index; --i) {
        node = node.prev;
      }
    }
    return node;
  }

  @Override
  public String toString() {
    StringBuilder str = new StringBuilder();
    str.append("size = ").append(size).append(", [");
    Node<E> node = first;
    for (int i = 0; i < size; ++i) {
      if (i != 0) str.append(", ");
      str.append(node);
      node = node.next;
    }
    str.append("]");
    return str.toString();
  }

  public void reset() {
    current = first;
  }

  public E next() {
    if (current == null) return null;
    current = current.next;
    return current.element;
  }

  public E remove() {
    if (current == null) return null;
    Node<E> next = current.next;
    E element = remove(current);
    current = size() == 0 ? null : next;
    return element;
  }

}
