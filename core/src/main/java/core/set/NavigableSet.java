package core.set;

import core.functional.Enumerable;

public interface NavigableSet<E> extends Set<E>, Enumerable<E> {
    int size();
}
