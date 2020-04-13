package core;

public interface Stack<E> {
    int size();

    boolean isEmpty();

    E top();

    void push(E element);

    E pop();
}
