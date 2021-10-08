package core;

public interface OrderedCollection<E> extends Collection<E> {
    static <E> boolean equals(final OrderedCollection<E> left, final OrderedCollection<E> right) {
        if (left == null && right == null) return true;
        if (left == null || right == null) return false;
        final var leftIt = left.iterator();
        final var rightIt = right.iterator();
        while (leftIt.hasNext() && rightIt.hasNext()) {
            if (!leftIt.next().equals(rightIt.next())) return false;
        }
        return !leftIt.hasNext() && !rightIt.hasNext();
    }
}
