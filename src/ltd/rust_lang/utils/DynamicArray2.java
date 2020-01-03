package ltd.rust_lang.utils;

public class DynamicArray2<E> extends DefaultList<E> {
    private static final int DEFAULT_CAPACITY = 10;

    protected E[] elements;

    public DynamicArray2(int capacity) {
        elements = (E[]) new Object[capacity];
    }

    public DynamicArray2() {
        this(DEFAULT_CAPACITY);
    }

    @Override
    public void insert(int index, E element) {
        if (element == null) return;

        rangeCheckInsert(index);
        ensureCapacity(size + 1);

        /*
        for (int i = size - 1; i >= index; --i) {
            elements[i + 1] = elements[i];
        }
         */
        if (size - index >= 0) System.arraycopy(elements, index, elements, index + 1, size - index);
        elements[index] = element;
        ++size;
    }

    @Override
    public E remove(int index) {
        rangeCheck(index);
        E old = elements[index];
        /*
        for (int i = index + 1; i < size; ++i) {
            elements[i - 1] = elements[i];
        }
         */
        if (size - index + 1 >= 0) System.arraycopy(elements, index + 1, elements, index, size - index + 1);
        elements[--size] = null;
        trim();
        return old;
    }

    @Override
    public E set(int index, E element) {
        rangeCheck(index);
        E old = elements[index];
        elements[index] = element;
        return old;
    }

    @Override
    public E get(int index) {
        rangeCheck(index);
        return elements[index];
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int indexOf(E element) {
        for (int i = 0; i < size; ++i) {
            if (elements[i].equals(element)) return i;
        }
        return ELEMENT_NOT_FOUND;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; ++i) {
            elements[i] = null;
        }
        trim();
        size = 0;
    }

    @Override
    public E remove(E element) {
        return remove(indexOf(element));
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("size = ").append(size).append(", ").append("elements = [");
        for (int i = 0; i < size; ++i) {
            str.append(elements[i]);
            if (i != size - 1) str.append(", ");
        }
        str.append("]");
        return str.toString();
    }

    private void ensureCapacity(int capacity) {
        int old = elements.length;
        if (old >= capacity) return;
        int newC = old + (old >> 1);
        E[] newElements = (E[]) new Object[newC];
        /*
        for (int i = 0; i < size; ++i) {
            newElements[i] = elements[i];
        }
         */
        if (size >= 0) System.arraycopy(elements, 0, newElements, 0, size);
        elements = newElements;
    }

    @Override
    public boolean contains(E element) {
        return indexOf(element) != ELEMENT_NOT_FOUND;
    }

    private void trim() {
        int old = elements.length;
        int newC = old >> 1;
        if (size > newC || old <= DEFAULT_CAPACITY) return;
        E[] newElements = (E[]) new Object[newC];
        if (size >= 0) System.arraycopy(elements, 0, newElements, 0, size);
        elements = newElements;
    }
}
