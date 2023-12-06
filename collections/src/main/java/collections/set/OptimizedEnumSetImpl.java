package collections.set;

class OptimizedEnumSetImpl<E extends Enum<E>> implements EnumSet<E> {
    @SafeVarargs
    private static <E extends Enum<E>> long computeMask(final E... enumValues) {
        long mask = 0;
        for (final var e : enumValues) {
            mask |= (1L << e.ordinal());
        }
        return mask;
    }

    private final long mask;

    @SafeVarargs
    OptimizedEnumSetImpl(final E... enumValues) {
        this(computeMask(enumValues));
    }

    private OptimizedEnumSetImpl(final long mask) {
        this.mask = mask;
    }

    @Override
    public Set<E> include(final E newElement) {
        return new OptimizedEnumSetImpl<>(this.mask | (1L << newElement.ordinal()));
    }

    @Override
    public Set<E> complement() {
        return new OptimizedEnumSetImpl<>(~this.mask);
    }

    @Override
    public Set<E> union(final Set<E> other) {
        return new OptimizedEnumSetImpl<>(this.mask | ((OptimizedEnumSetImpl<E>) other).mask);
    }

    @Override
    public Set<E> intersect(final Set<E> other) {
        return new OptimizedEnumSetImpl<>(this.mask & ((OptimizedEnumSetImpl<E>) other).mask);
    }

    @Override
    public Set<E> difference(final Set<E> other) {
        return new OptimizedEnumSetImpl<>(this.mask & ~(((OptimizedEnumSetImpl<E>) other).mask));
    }

    @Override
    public boolean contains(final E element) {
        return (mask & (1L << element.ordinal())) != 0;
    }
}
