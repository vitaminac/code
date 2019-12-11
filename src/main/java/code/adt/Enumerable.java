package code.adt;

import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public interface Enumerable<E> extends Iterable<E> {
    void enumerate(Consumer<E> consumer);

    default Enumerable<E> map(UnaryOperator<E> operator) {
        return consumer -> this.enumerate(e -> consumer.accept(operator.apply(e)));
    }

    abstract class Reducer<E, R> implements Consumer<E>, Supplier<R> {
        protected R result;

        private Reducer(R initial) {
            this.result = initial;
        }

        @Override
        public R get() {
            return this.result;
        }
    }

    default <R> R reduce(R identity, BiFunction<E, R, R> accumulator) {
        Reducer<E, R> reducer = new Reducer<E, R>(identity) {
            @Override
            public void accept(E e) {
                this.result = accumulator.apply(e, this.result);
            }
        };
        this.forEach(reducer);
        return reducer.get();
    }

    @Override
    default Iterator<E> iterator() {
        Queue<E> queue = new ArrayList<>();
        this.enumerate(queue::enqueue);
        return queue.iterator();
    }
}
