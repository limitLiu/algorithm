package ltd.rust_lang.utils.list;

public class CircleSinglyLinkList<E> extends DefaultList<E> {

  private Node<E> first;
  private Node<E> current;

  private static class Node<E> {
    E element;
    Node<E> next;

    Node(E element, Node<E> next) {
      this.element = element;
      this.next = next;
    }
  }

  @Override
  public boolean isEmpty() {
    return size() == 0;
  }

  @Override
  public void insert(int index, E element) {
    rangeCheckInsert(index);
    if (index == 0) {
      Node<E> newFirst = new Node<>(element, first);
      Node<E> last = size() == 0 ? newFirst : getNode(size() - 1);
      last.next = newFirst;
      first = newFirst;
    } else {
      Node<E> prev = getNode(index - 1);
      prev.next = new Node<>(element, prev.next);
    }
    ++size;
  }

  @Override
  public boolean add(E element) {
    insert(size(), element);
    return true;
  }

  @Override
  public E remove(int index) {
    rangeCheck(index);
    Node<E> deleted = first;
    if (index == 0) {
      if (size() == 0) {
        first = null;
      } else {
        Node<E> last = getNode(size() - 1);
        first = first.next;
        last.next = first;
      }
    } else {
      Node<E> prev = getNode(index - 1);
      deleted = prev.next;
      prev.next = deleted.next;
    }
    --size;
    return deleted.element;
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
  public int size() {
    return this.size;
  }

  @Override
  public E remove(E element) {
    return remove(indexOf(element));
  }

  private Node<E> getNode(int index) {
    rangeCheck(index);
    Node<E> node = first;
    for (int i = 0; i < index; i++) {
      node = node.next;
    }
    return node;
  }

  @Override
  public String toString() {
    StringBuilder str = new StringBuilder();
    str.append("size = ").append(size).append(", ").append("[");
    Node<E> node = first;
    for (int i = 0; i < size; ++i) {
      str.append(node.element);
      if (i != size - 1) str.append(", ");
      node = node.next;
    }
    str.append("]");
    return str.toString();
  }
}
