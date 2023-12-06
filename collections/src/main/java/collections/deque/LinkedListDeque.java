package collections.deque;

import collections.linkedlist.DoublyLinkedList;

import java.util.Iterator;
import java.util.function.Supplier;

public class LinkedListDeque<E>
        implements Deque<E> {
    private final DoublyLinkedList<E> doublyLinkedList;

    public LinkedListDeque(final Supplier<DoublyLinkedList<E>> supplier) {
        doublyLinkedList = supplier.get();
    }

    @Override
    public E getFirst() {
        return doublyLinkedList.getFirst();
    }

    @Override
    public E getLast() {
        return doublyLinkedList.getLast();
    }

    @Override
    public void addFirst(E element) {
        doublyLinkedList.addFirst(element);
    }

    @Override
    public void addLast(E element) {
        doublyLinkedList.addLast(element);
    }

    @Override
    public E removeFirst() {
        return doublyLinkedList.removeFirst();
    }

    @Override
    public E removeLast() {
        return doublyLinkedList.removeLast();
    }

    @Override
    public boolean isEmpty() {
        return doublyLinkedList.isEmpty();
    }

    @Override
    public int size() {
        return doublyLinkedList.size();
    }

    @Override
    public Iterator<E> iterator() {
        return doublyLinkedList.iterator();
    }
}
