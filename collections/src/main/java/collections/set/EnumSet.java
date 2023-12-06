package collections.set;

import core.Arrays;

public interface EnumSet<E extends Enum<E>> extends Set<E> {
    @SafeVarargs
    static <E extends Enum<E>> Set<E> frozenSetOf(final E first, final E... rest) {
        @SuppressWarnings("unchecked") final Class<E> enumType = (Class<E>) first.getClass();
        final E[] allEnumValues = enumType.getEnumConstants();
        if (allEnumValues.length > 64) {
            return Set.frozenSetOf(Arrays.prepend(first, rest));
        } else {
            return new OptimizedEnumSetImpl<E>(Arrays.prepend(first, rest));
        }
    }

    static <E extends Enum<E>> Set<E> emptySet(final Class<E> enumType) {
        final E[] allEnumValues = enumType.getEnumConstants();
        if (allEnumValues.length > 64) {
            return Set.emptySet();
        } else {
            return new OptimizedEnumSetImpl<>();
        }
    }

    static <E extends Enum<E>> Set<E> allOf(final Class<E> enumType) {
        final E[] allEnumValues = enumType.getEnumConstants();
        if (allEnumValues.length > 64) {
            return Set.frozenSetOf(allEnumValues);
        } else {
            return (new OptimizedEnumSetImpl<E>(allEnumValues));
        }
    }
}
