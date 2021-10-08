package core.set;

import core.functional.Enumerable;

public interface NavigableSet<E> extends Set<E>, Enumerable<E> {
    int size();

    static <E> boolean equals(final NavigableSet<E> left, final NavigableSet<E> right) {
        if (left == null && right == null) return true;
        if (left == null || right == null) return false;
        for (final var item : left) {
            if (!right.contains(item)) {
                return false;
            }
        }
        for (final var item : right) {
            if (!left.contains(item)) {
                return false;
            }
        }
        return true;
    }
}
