package code.adt;

public interface PositionList<E, P extends Position<E>> {
    int size();

    boolean isEmpty();

    Position<E> firstPosition();

    Position<E> lastPosition();

    Position<E> before(P position);

    Position<E> after(P position);

    Position<E> insertBefore(P position, E element);

    Position<E> insertAfter(P position, E element);
}
