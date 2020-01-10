package ltd.rust_lang.utils.queue;

import org.jetbrains.annotations.Contract;

public class CircleDeque<E> {

  private int size;
  private int front;
  private E[] elements;

  public CircleDeque() {
    elements = (E[]) new Object[10];
  }

  public void clear() {
    for (int i = 0; i < size; ++i) {
      elements[idx(i)] = null;
    }
    front = 0;
    size = 0;
  }

  @Contract(pure = true)
  private int idx(int i) {
    var index = i + front;
    if (index < 0) return index + elements.length;
//    return index % elements.length;
    return index - (elements.length > index ? 0 : elements.length);
  }

  public int size() {
    return size;
  }

  public boolean isEmpty() {
    return size() == 0;
  }

  public E rear() {
    return elements[idx(size() - 1)];
  }

  public E front() {
    return elements[front];
  }

  public void enQueueFront(E e) {
    ensure(size() - 1);
    front = idx(-1);
    elements[front] = e;
    size++;
  }

  public void enQueueRear(E element) {
    ensure(size() + 1);
    elements[idx(size)] = element;
    size++;
  }

  public E deQueueRear() {
    var i = idx(size() - 1);
    E rear = elements[idx(i)];
    elements[i] = null;
    return rear;
  }

  public E deQueueFront() {
    E frontE = elements[front];
    elements[front] = null;
    front = idx(1);
    size--;
    return frontE;
  }

  @Override
  public String toString() {
    StringBuilder str = new StringBuilder();
    var len = elements.length;
    str.append("capacity = ")
        .append(len)
        .append(" size = ").append(size)
        .append(" front = ").append(front)
        .append(", [");
    for (var i = 0; i < len; ++i) {
      if (i != 0) str.append(", ");
      str.append(elements[i]);
    }
    str.append("]");
    return str.toString();
  }

  private void ensure(int capacity) {
    var old = elements.length;
    if (old >= capacity) return;
    var newC = old + (old >> 1);
    E[] newElements = (E[]) new Object[newC];
    for (var i = 0; i < size; ++i) {
      newElements[i] = elements[idx(i)];
    }
    elements = newElements;
    front = 0;
  }
}
