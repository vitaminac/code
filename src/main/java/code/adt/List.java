package code.adt;

public interface List<E> extends Enumerable<E>, RandomAccess<E> {
    int size();

    boolean isEmpty();

    void clear();

    void insert(int index, E element);

    E remove(int index);

    int find(E element);
}
