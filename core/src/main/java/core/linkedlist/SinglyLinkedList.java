package core.linkedlist;

import java.util.Iterator;
import java.util.NoSuchElementException;

import core.behaviour.OrderedCollection;

public interface SinglyLinkedList<E> extends OrderedCollection<E> {
    E first();

    SinglyLinkedList<E> rest();

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

            @Override
            public Iterator<E> iterator() {
                final var restIt = this.rest().iterator();
                return new Iterator<E>() {
                    private boolean consumed = false;

                    @Override
                    public boolean hasNext() {
                        if (consumed) {
                            return restIt.hasNext();
                        } else {
                            return true;
                        }
                    }

                    @Override
                    public E next() {
                        if (consumed) {
                            return restIt.next();
                        } else {
                            consumed = true;
                            return first;
                        }
                    }
                };
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

            @Override
            public Iterator<E> iterator() {
                return new Iterator<E>() {
                    @Override
                    public boolean hasNext() {
                        return false;
                    }

                    @Override
                    public E next() {
                        throw new NoSuchElementException();
                    }
                };
            }
        };
    }
}
