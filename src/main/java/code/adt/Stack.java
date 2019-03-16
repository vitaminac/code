package code.adt;

public interface Stack<E> extends Iterable<E> {
    int size();

    boolean isEmpty();

    // TODO: top
    E peek();

    void push(E element);

    E pop();
}
