package code.adt;

public interface List<E> extends Enumerable<E>, RandomAccess<E> {
    boolean isEmpty();

    void clear();

    void insert(int index, E element);

    E remove(int index);

    int indexOf(E element);
}
