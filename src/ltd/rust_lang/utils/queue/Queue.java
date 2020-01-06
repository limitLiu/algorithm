package ltd.rust_lang.utils.queue;

import ltd.rust_lang.utils.list.DoublyLinkList;
import ltd.rust_lang.utils.list.MyList;

public class Queue<E> {
  private MyList<E> list = new DoublyLinkList<>();

  public int size() {
    return list.size();
  }

  public boolean isEmpty() {
    return list.isEmpty();
  }

  public void enQueue(E element) {
    list.add(element);
  }

  public E deQueue() {
    return list.remove(0);
  }

  public E front() {
    return list.get(0);
  }

  public void clear() {
    list.clear();
  }
}
