package collections.deque;

import collections.linkedlist.SinglyLinkedListDoubleReference;

import java.util.Iterator;
import java.util.function.Supplier;

public class LinkedListSteque<E>
        implements Steque<E> {
    private final SinglyLinkedListDoubleReference<E> singlyLinkedListDoubleReference;

    public LinkedListSteque(final Supplier<SinglyLinkedListDoubleReference<E>> supplier) {
        singlyLinkedListDoubleReference = supplier.get();
    }

    @Override
    public E peek() {
        return singlyLinkedListDoubleReference.getFirst();
    }

    @Override
    public void push(final E element) {
        singlyLinkedListDoubleReference.appendFirst(element);
    }

    @Override
    public E pop() {
        return singlyLinkedListDoubleReference.removeFirst();
    }

    @Override
    public void append(final E element) {
        singlyLinkedListDoubleReference.appendLast(element);
    }

    @Override
    public boolean isEmpty() {
        return singlyLinkedListDoubleReference.isEmpty();
    }

    @Override
    public int size() {
        return singlyLinkedListDoubleReference.size();
    }

    @Override
    public Iterator<E> iterator() {
        return singlyLinkedListDoubleReference.iterator();
    }
}
