package code.adt;

public interface Stack<E> extends Enumerable<E> {
    int size();

    boolean isEmpty();

    E top();

    void push(E element);

    E pop();
}
