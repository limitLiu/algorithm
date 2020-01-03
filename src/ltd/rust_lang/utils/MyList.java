package ltd.rust_lang.utils;

public interface MyList<E> {

    int ELEMENT_NOT_FOUND = -1;

    boolean isEmpty();

    void insert(int index, E element);

    boolean add(E element);

    E remove(int index);

    E set(int index, E element);

    E get(int index);

    void clear();

    boolean contains(E element);

    int indexOf(E element);

    int size();

    E remove(E element);
}
