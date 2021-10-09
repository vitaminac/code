package core.bag;

import java.util.Iterator;

import core.behaviour.Collection;
import core.linkedlist.SinglyLinkedListDoubleReference;

public interface Bag<E> extends Collection<E> {
    void add(E element);

    static <E> Bag<E> fromSinglyLinkedListDoubleReference() {
        final var list = new SinglyLinkedListDoubleReference<E>();
        return new Bag<E>() {
            @Override
            public void add(E element) {
                list.prependHead(element);
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
                return list.iterator();
            }
        };
    }
}
