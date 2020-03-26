package code.adt;

public interface Stack<E> extends Enumerable<E> {
    boolean isEmpty();

    E top();

    void push(E element);

    E pop();
}
