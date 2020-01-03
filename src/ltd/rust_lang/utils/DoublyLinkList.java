package ltd.rust_lang.utils;

public class DoublyLinkList<E> extends DefaultList<E> {

  private Node<E> first;
  private Node<E> last;

  private static class Node<E> {
    E element;
    Node<E> prev;
    Node<E> next;

    Node(E element, Node<E> prev, Node<E> next) {
      this.element = element;
      this.next = next;
      this.prev = prev;
    }
  }

  @Override
  public void insert(int index, E element) {
    rangeCheckInsert(index);

    if (index == size()) {
      Node<E> old = last;
      last = new Node<>(element, old, null);
      if (old == null) {
        first = last;
      } else {
        old.next = last;
      }
    } else {
      Node<E> next = getNode(index);
      Node<E> prev = next.prev;
      Node<E> newNode = new Node<>(element, prev, next);
      next.prev = newNode;
      if (prev == null) {
        first = newNode;
      } else {
        prev.next = newNode;
      }
    }
    ++size;
  }

  @Override
  public E remove(int index) {
    Node<E> current = getNode(index);
    Node<E> next = current.next;
    Node<E> prev = current.prev;
    if (prev == null) {
      first = next;
      next.prev = null;
    } else if (next == null) {
      last = prev;
      prev.next = null;
    } else {
      prev.next = next;
      next.prev = prev;
    }
    --size;
    return current.element;
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
    str.append("size = ").append(size).append(", ").append("[");
    Node<E> node = first.next;
    for (int i = 0; i < size; ++i) {
      str.append(node.element);
      if (i != size - 1) str.append(", ");
      node = node.next;
    }
    str.append("]");
    return str.toString();
  }
}
