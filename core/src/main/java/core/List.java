package core;

public interface List<E> extends Enumerable<E>, RandomAccess<E> {
    boolean isEmpty();

    void clear();

    void insert(int index, E element);

    E remove(int index);

    int indexOf(E element);

    int lastIndexOf(E element);

    private static <E> void swapHelper(List<E> list, int i, int j) {
        list.set(i, list.set(j, list.get(i)));
    }

    static void swap(List<?> list, int i, int j) {
        swapHelper(list, i, j);
    }
}
