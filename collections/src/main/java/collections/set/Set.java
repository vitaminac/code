package collections.set;

public interface Set<E> {
    static <E> Set<E> emptySet() {
        return element -> false;
    }

    @SafeVarargs
    static <E> Set<E> frozenSetOf(final E... elements) {
        return e -> {
            for (final var element : elements) {
                if (e.equals(element)) return true;
            }
            return false;
        };
    }

    boolean contains(E element);

    default Set<E> include(E newElement) {
        return e -> e.equals(newElement) || this.contains(e);
    }

    default Set<E> complement() {
        return e -> !this.contains(e);
    }

    default Set<E> union(Set<E> other) {
        return e -> this.contains(e) || other.contains(e);
    }

    default Set<E> intersect(Set<E> other) {
        return e -> this.contains(e) && other.contains(e);
    }

    default Set<E> difference(Set<E> other) {
        return e -> this.contains(e) && !other.contains(e);
    }

    default Set<E> symmetricDifference(Set<E> other) {
        return this.difference(other).union(other.difference(this));
    }
}
