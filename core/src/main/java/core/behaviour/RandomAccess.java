package core.behaviour;

public interface RandomAccess<E> {
    E get(int index);

    E set(int index, E element);
}
