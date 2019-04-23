package code.adt;

import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public interface Enumerable<E> extends Iterable<E> {
    void enumerate(Consumer<E> consumer);

    default Enumerable<E> map(UnaryOperator<E> operator) {
        return consumer -> this.enumerate(e -> consumer.accept(operator.apply(e)));
    }

    @Override
    default Iterator<E> iterator() {
        Queue<E> queue = new ArrayList<>();
        this.enumerate(queue::enqueue);
        return queue.iterator();
    }
}
