package core.set;

import core.Enumerable;

public interface Set<E> extends Enumerable<E> {
    boolean isEmpty();

    boolean contains(E element);

    void add(E element);

    void remove(E element);

    void clear();

    default boolean belongTo(Set<E> that) {
        return this.all(that::contains);
    }

    default void union(Set<E> that) {
        that.forEach(this::add);
    }
}
