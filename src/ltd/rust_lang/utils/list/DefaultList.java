package ltd.rust_lang.utils.list;

public abstract class DefaultList<E> implements MyList<E> {
    protected int size = 0;

    void rangeCheck(int index) {
        if (index >= size || index < 0) outBounds(index);
    }

    void rangeCheckInsert(int index) {
        if (index > size || index < 0) outBounds(index);
    }

    private void outBounds(int index) {
        throw new IndexOutOfBoundsException("index: " + index + ", size: " + size);
    }

    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean add(E element) {
        insert(size, element);
        return true;
    }
}
