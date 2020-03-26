package code.adt;

public interface RandomAccess<E> {
    E get(int index);

    void set(int index, E element);
}
