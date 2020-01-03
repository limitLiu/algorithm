package ltd.rust_lang.utils;

import ltd.rust_lang.utils.list.DynamicArray;
import ltd.rust_lang.utils.list.MyList;

public class Stack<E> implements IStack<E> {
  private MyList<E> list = new DynamicArray<>();

  @Override
  public E pop() {
    return list.remove(size() - 1);
  }

  @Override
  public void push(E e) {
    list.add(e);
  }

  @Override
  public int size() {
    return list.size();
  }

  @Override
  public boolean isEmpty() {
    return list.isEmpty();
  }

  @Override
  public E top() {
    return list.get(size() - 1);
  }
}
