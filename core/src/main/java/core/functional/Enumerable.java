package core.functional;

import java.util.function.Consumer;

import core.linkedlist.SinglyLinkedListDoubleReference;

@FunctionalInterface
public interface Enumerable<E> extends Iterable<E> {
    void enumerate(final Consumer<? super E> consumer);

    @Override
    default Iterator<E> iterator() {
        // TODO: replace by Generator
        final SinglyLinkedListDoubleReference<E> linkedList = new SinglyLinkedListDoubleReference<>();
        this.enumerate(linkedList::appendTail);
        return linkedList.iterator();
    }
}
