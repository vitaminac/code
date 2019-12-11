package code.adt;

public interface PositionList<E> extends Iterable<E> {
    int size();

    boolean isEmpty();

    Position<E> firstPosition();

    Position<E> lastPosition();

    Position<E> before(Position<E> position);

    Position<E> after(Position<E> position);

    Position<E> insertBefore(Position<E> position, E element);

    Position<E> insertAfter(Position<E> position, E element);

    void remove(Position<E> position);
}
