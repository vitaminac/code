package core.stack;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

import core.behaviour.Collection;
import core.deque.Deque;
import core.linkedlist.SinglyLinkedListDoubleReference;
import core.list.List;

public interface Stack<E> extends Collection<E> {
    E peek();

    void push(E element);

    E pop();

    static <E> Stack<E> fromDeque(final Supplier<Deque<E>> supplier) {
        final var deque = supplier.get();
        return new Stack<E>() {
            @Override
            public int size() {
                return deque.size();
            }

            @Override
            public boolean isEmpty() {
                return deque.isEmpty();
            }

            @Override
            public E peek() {
                return deque.getFirst();
            }

            @Override
            public void push(E element) {
                deque.addFirst(element);
            }

            @Override
            public E pop() {
                return deque.removeFirst();
            }

            @Override
            public Iterator<E> iterator() {
                return deque.iterator();
            }
        };
    }

    static <E> Stack<E> fromSinglyLinkedListDoubleReference() {
        final SinglyLinkedListDoubleReference<E> list = new SinglyLinkedListDoubleReference<>();
        return new Stack<E>() {
            @Override
            public int size() {
                return list.size();
            }

            @Override
            public boolean isEmpty() {
                return list.isEmpty();
            }

            @Override
            public E peek() {
                return list.peek();
            }

            @Override
            public void push(E element) {
                list.prependHead(element);
            }

            @Override
            public E pop() {
                return list.removeHead();
            }

            @Override
            public Iterator<E> iterator() {
                return list.iterator();
            }
        };
    }

    static <E> Stack<E> fromList(final Supplier<List<E>> supplier) {
        final var list = supplier.get();
        return new Stack<E>() {
            @Override
            public E peek() {
                return list.get(list.size() - 1);
            }

            @Override
            public void push(E element) {
                list.add(list.size(), element);
            }

            @Override
            public E pop() {
                return list.remove(list.size() - 1);
            }

            @Override
            public boolean isEmpty() {
                return list.isEmpty();
            }

            @Override
            public int size() {
                return list.size();
            }

            @Override
            public Iterator<E> iterator() {
                return new Iterator<E>() {
                    private int index = list.size() - 1;

                    @Override
                    public boolean hasNext() {
                        return this.index >= 0;
                    }

                    @Override
                    public E next() {
                        if (this.index < 0) throw new NoSuchElementException();
                        return list.get(index--);
                    }
                };
            }
        };
    }
}
