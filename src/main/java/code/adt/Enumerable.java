package code.adt;

import java.util.Iterator;
import java.util.function.*;

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

    default Enumerable<E> filter(Predicate<E> predicate) {
        return consumer -> this.enumerate(e -> {
            if (predicate.test(e)) {
                consumer.accept(e);
            }
        });
    }

    @Override
    default Iterator<E> iterator() {
        Queue<E> queue = new ArrayList<>();
        this.enumerate(queue::enqueue);
        return queue.iterator();
    }
}
