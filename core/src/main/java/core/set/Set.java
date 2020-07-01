package core.set;

import java.util.function.Function;
import java.util.function.Predicate;

public interface Set<E> {
    static <E> Set<E> emptySet() {
        return element -> false;
    }

    @SuppressWarnings("unchecked")
    static <E> Set<E> of(E... elements) {
        return predicate -> {
            for (var e : elements) {
                if (predicate.test(e)) return true;
            }
            return false;
        };
    }

    static <E> Set<E> include(Set<E> set, E newElement) {
        return predicate -> predicate.test(newElement) || set.match(predicate);
    }

    static <E> Set<E> union(Set<E> s1, Set<E> s2) {
        return predicate -> s1.match(predicate) || s2.match(predicate);
    }

    static <E> Set<E> intersect(Set<E> s1, Set<E> s2) {
        return predicate -> s1.match(predicate) && s2.match(predicate);
    }

    static <E> Set<E> diff(Set<E> s1, Set<E> s2) {
        return predicate -> s1.match(predicate) && !s2.match(predicate);
    }

    static <E> Set<E> filter(Set<E> set, Predicate<E> filter) {
        return predicate -> set.match(e -> filter.test(e) && predicate.test(e));
    }

    static <E, R> Set<R> map(Set<E> set, Function<E, R> f) {
        return predicate -> set.match(e -> predicate.test(f.apply(e)));
    }

    boolean match(Predicate<E> predicate);

    default boolean contains(E element) {
        return match(e -> e == element);
    }
}
