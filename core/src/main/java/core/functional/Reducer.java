package core.functional;

import java.util.function.BiFunction;
import java.util.function.Consumer;

public class Reducer<E, R> implements Consumer<E> {
    private final BiFunction<E, R, R> accumulator;
    private R accumulated;

    public Reducer(final R identity, final BiFunction<E, R, R> accumulator) {
        this.accumulated = identity;
        this.accumulator = accumulator;
    }

    @Override
    public void accept(E e) {
        this.accumulated = this.accumulator.apply(e, this.accumulated);
    }

    public R getAccumulated() {
        return this.accumulated;
    }
}
