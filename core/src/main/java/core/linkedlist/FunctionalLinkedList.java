package core.linkedlist;

import java.util.Iterator;
import java.util.NoSuchElementException;

import core.behaviour.OrderedCollection;

public interface FunctionalLinkedList<E> extends OrderedCollection<E> {
    E peek();

    FunctionalLinkedList<E> rest();

    default FunctionalLinkedList<E> construct(final E first) {
        return new FunctionalLinkedList<E>() {
            @Override
            public E peek() {
                return first;
            }

            @Override
            public FunctionalLinkedList<E> rest() {
                return FunctionalLinkedList.this;
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

    static <E> FunctionalLinkedList<E> emptyList() {
        return new FunctionalLinkedList<E>() {
            @Override
            public E peek() {
                throw new NoSuchElementException();
            }

            @Override
            public FunctionalLinkedList<E> rest() {
                throw new NoSuchElementException();
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
