package code.adt;

public interface List<E> extends Iterable<E> {
    int size();

    boolean isEmpty();

    E get(int index);

    void set(int index, E element);

    void add(int index, E element);

    E remove(int index);
}
