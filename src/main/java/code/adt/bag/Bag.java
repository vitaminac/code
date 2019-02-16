package code.adt.bag;

public interface Bag<E> extends Iterable<E> {
    void add(E item);

    boolean isEmpty();

    int size();
}
