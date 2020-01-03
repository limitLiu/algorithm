package ltd.rust_lang.utils;

public interface IStack<E> {
  E pop();

  void push(E e);

  int size();

  boolean isEmpty();

  E top();
}
