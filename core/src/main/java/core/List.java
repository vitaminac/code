package core;

public interface List<E> extends Enumerable<E>, RandomAccess<E> {
    boolean isEmpty();

    void clear();

    void insert(int index, E element);

    E remove(int index);

    int indexOf(E element);

    int lastIndexOf(E element);

    default void swap(int i, int j) {
        this.set(i, this.set(j, this.get(i)));
    }
}
