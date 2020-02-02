package code.adt;

import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Enumerable<E> extends Iterable<E> {
    void enumerate(Consumer<? super E> consumer);

    default int size() {
        int[] ref = new int[]{0};
        this.enumerate(e -> ref[0]++);
        return ref[0];
    }

    default <R> Enumerable<R> map(Function<E, R> operator) {
        return consumer -> this.enumerate(e -> consumer.accept(operator.apply(e)));
    }

    @SuppressWarnings("unchecked")
    default <R> R reduce(R identity, BiFunction<E, R, R> accumulator) {
        R[] ref = (R[]) new Object[]{identity};
        this.enumerate(e -> ref[0] = accumulator.apply(e, ref[0]));
        return ref[0];
    }

    default Enumerable<E> filter(Predicate<E> predicate) {
        return consumer -> this.enumerate(e -> {
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
        this.enumerate(queue::enqueue);
        return queue.iterator();
    }

    @Override
    default void forEach(Consumer<? super E> consumer){
        this.enumerate(consumer);
    }

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
