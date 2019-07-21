package code.adt;

public interface PositionList<E> {
    int size();

    boolean isEmpty();

    Position<E> firstPosition();

    Position<E> lastPosition();

    Position<E> first(E element);

    Position<E> before(Position<E> position);

    Position<E> after(Position<E> position);

    Position<E> insertBefore(Position<E> position, E element);

    Position<E> insertAfter(Position<E> position, E element);
}
