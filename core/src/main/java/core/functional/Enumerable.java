package core.functional;

import java.util.function.Consumer;

@FunctionalInterface
public interface Enumerable<E> extends Iterable<E> {
    void enumerate(final Consumer<? super E> consumer);

    @Override
    default Iterator<E> iterator() {
        return new Generator<E>() {
            @Override
            public void generate() {
                Enumerable.this.enumerate(this::yieldValue);
            }
        };
    }
}
