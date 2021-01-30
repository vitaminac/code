package core.linkedlist;

import java.util.NoSuchElementException;

public interface SinglyLinkedList<E> {
    E first();

    SinglyLinkedList<E> rest();

    int size();

    boolean isEmpty();

    default SinglyLinkedList<E> construct(final E first) {
        return new SinglyLinkedList<E>() {
            @Override
            public E first() {
                return first;
            }

            @Override
            public SinglyLinkedList<E> rest() {
                return SinglyLinkedList.this;
            }

            @Override
            public int size() {
                return this.rest().size() + 1;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }
        };
    }

    static <E> SinglyLinkedList<E> emptyList() {
        return new SinglyLinkedList<E>() {
            @Override
            public E first() {
                throw new NoSuchElementException();
            }

            @Override
            public SinglyLinkedList<E> rest() {
                return this;
            }

            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return true;
            }
        };
    }
}
