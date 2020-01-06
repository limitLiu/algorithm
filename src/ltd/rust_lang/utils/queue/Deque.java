package ltd.rust_lang.utils.queue;

import ltd.rust_lang.utils.list.CircleDoublyLinkList;
import ltd.rust_lang.utils.list.MyList;

public class Deque<E> {
  private MyList<E> list = new CircleDoublyLinkList<>();

  public int size() {
    return list.size();
  }

  public boolean isEmpty() {
    return list.isEmpty();
  }

  public void enQueueRear(E element) {
    list.add(element);
  }

  public E deQueueFront() {
    return list.remove(0);
  }

  public void enQueueFront(E element) {
    list.insert(0, element);
  }

  public E deQueueRear() {
    return list.remove(list.size() - 1);
  }

  public E front() {
    return list.get(0);
  }

  public E rear() {
    return list.get(size() - 1);
  }

  public void clear() {
    list.clear();
  }
}
