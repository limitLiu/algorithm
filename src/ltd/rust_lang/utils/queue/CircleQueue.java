package ltd.rust_lang.utils.queue;

import org.jetbrains.annotations.Contract;

public class CircleQueue<E> {
  private int size;
  private int front;
  private E[] elements;

  public CircleQueue() {
    elements = (E[]) new Object[10];
  }

  @Contract(pure = true)
  private int idx(int i) {
    var index = i + front;
    return index - (index >= elements.length ? elements.length : 0);
  }

  public int size() {
    return size;
  }

  public boolean isEmpty() {
    return size() == 0;
  }

  public void enQueue(E element) {
    ensure(size + 1);
    elements[idx(size)] = element;
    size++;
  }

  private void ensure(int capacity) {
    int old = elements.length;
    if (old >= capacity) return;
    var newC = old + (old >> 1);
    E[] newElements = (E[]) new Object[newC];
    for (int i = 0; i < size; ++i) {
      newElements[i] = elements[idx(i)];
    }
    elements = newElements;
    front = 0;
  }

  public E deQueue() {
    E frontEle = elements[front];
    elements[front] = null;
    front = idx(1);
    size--;
    return frontEle;
  }

  public E front() {
    return elements[front];
  }

  public void clear() {
    for (int i = 0; i < size; ++i) {
      elements[idx(i)] = null;
    }
    front = 0;
    size = 0;
  }

  @Override
  public String toString() {
    StringBuilder str = new StringBuilder();
    int len = elements.length;
    str.append("capacity = ")
        .append(len)
        .append(" size = ").append(size)
        .append(" front = ").append(front)
        .append(", [");
    for (int i = 0; i < len; ++i) {
      if (i != 0) str.append(", ");
      str.append(elements[i]);
    }
    str.append("]");
    return str.toString();
  }
}
