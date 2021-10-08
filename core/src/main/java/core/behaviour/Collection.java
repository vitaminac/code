package core.behaviour;

import core.functional.Iterable;

public interface Collection<E> extends Iterable<E> {
    boolean isEmpty();

    int size();
}
