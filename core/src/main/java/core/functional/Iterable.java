package core.functional;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import core.util.Iterators;

@FunctionalInterface
public interface Iterable<E> extends java.lang.Iterable<E> {
    @Override
    Iterator<E> iterator();

    default Iterable<E> filter(final Predicate<E> predicate) {
        final var it = this.iterator();
        return () -> new Iterator<>() {
            private E next;

            @Override
            public boolean hasNext() {
                while (it.hasNext()) {
                    final var tmp = it.next();
                    if (predicate.test(tmp)) {
                        this.next = tmp;
                        return true;
                    }
                }
                return false;
            }

            @Override
            public E next() {
                if (this.next == null) {
                    throw new NoSuchElementException();
                }
                final var result = this.next;
                this.next = null;
                return result;
            }
        };
    }

    default <R> Iterable<R> map(final Function<E, R> operator) {
        final var it = this.iterator();
        return () -> new Iterator<>() {
            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override
            public R next() {
                return operator.apply(it.next());
            }
        };
    }

    default Iterable<E> touch(Consumer<? super E> consumer) {
        return this.map(e -> {
            consumer.accept(e);
            return e;
        });
    }

    default <R> R reduce(final R identity, final BiFunction<E, R, R> accumulator) {
        final Reducer<E, R> reducer = new Reducer<>(identity, accumulator);
        this.forEach(reducer);
        return reducer.getAccumulated();
    }

    default boolean all(Predicate<E> predicate) {
        for (E e : this) {
            if (!predicate.test(e)) return false;
        }
        return true;
    }

    default boolean some(Predicate<E> predicate) {
        for (E e : this) {
            if (predicate.test(e)) return true;
        }
        return false;
    }

    static Iterable<Integer> range(final int start, final int stop, final int step) {
        return () -> new Iterator<>() {
            private int next = start;

            @Override
            public boolean hasNext() {
                return this.next < stop;
            }

            @Override
            public Integer next() {
                if (this.next >= stop) {
                    throw new NoSuchElementException();
                }
                final var result = this.next;
                this.next += step;
                return result;
            }
        };
    }

    static Iterable<Double> range(final double start, final double stop, final double step) {
        return () -> new Iterator<>() {
            private double next = start;

            @Override
            public boolean hasNext() {
                return this.next < stop;
            }

            @Override
            public Double next() {
                if (this.next >= stop) {
                    throw new NoSuchElementException();
                }
                final var result = this.next;
                this.next += step;
                return result;
            }
        };
    }

    static Iterable<Double> linspace(final double start, final double stop, int n) {
        final double step = (stop - start) / n;
        return range(start, stop, step);
    }

    static <E> Iterable<E> from(final E[] arr) {
        return () -> Iterators.from(arr);
    }

    @SafeVarargs
    static <E> Iterable<E> flatten(final Enumerable<? extends E>... enumerables) {
        return () -> {
            @SuppressWarnings("unchecked") final var it = (Iterator<E>) Iterators.flatten(Arrays.stream(enumerables)
                    .map(Iterable::iterator)
                    .toArray(Iterator[]::new));
            return it;
        };
    }
}
