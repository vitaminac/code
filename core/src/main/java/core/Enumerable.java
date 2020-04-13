package core;

import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

@FunctionalInterface
public interface Enumerable<E> extends Iterable<E> {
    default int size() {
        int[] ref = new int[]{0};
        this.forEach(e -> ref[0]++);
        return ref[0];
    }

    default <R> Enumerable<R> map(Function<E, R> operator) {
        return consumer -> this.forEach(e -> consumer.accept(operator.apply(e)));
    }

    @SuppressWarnings("unchecked")
    default <R> R reduce(R identity, BiFunction<E, R, R> accumulator) {
        R[] ref = (R[]) new Object[]{identity};
        this.forEach(e -> ref[0] = accumulator.apply(e, ref[0]));
        return ref[0];
    }

    default Enumerable<E> filter(Predicate<E> predicate) {
        return consumer -> this.forEach(e -> {
            if (predicate.test(e)) {
                consumer.accept(e);
            }
        });
    }

    default Enumerable<E> touch(Consumer<? super E> consumer) {
        return this.map(e -> {
            consumer.accept(e);
            return e;
        });
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

    @Override
    default Iterator<E> iterator() {
        Queue<E> queue = new SinglyLinkedList<>();
        this.forEach(queue::enqueue);
        return queue.iterator();
    }

    @Override
    void forEach(Consumer<? super E> consumer);

    static Enumerable<Integer> range(final int start, final int stop, final int step) {
        return consumer -> {
            for (int i = start; i < stop; i += step) {
                consumer.accept(i);
            }
        };
    }

    static Enumerable<Double> linspace(final double start, final double stop, int n) {
        final double step = (stop - start) / n;
        return consumer -> {
            for (double i = start; i < stop; i += step) {
                consumer.accept(step);
            }
        };
    }

    static <E> Enumerable<E> from(E[] arr) {
        return consumer -> {
            for (E e : arr) consumer.accept(e);
        };
    }
}
