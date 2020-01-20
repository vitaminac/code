package code.adt.pq;

import code.adt.ArrayList;
import code.adt.List;
import code.algorithm.divideandconquer.BinarySearch;

import java.util.Comparator;

public class SortedList<E> implements PriorityQueue<E> {
    private final List<E> list = new ArrayList<>();
    private final BinarySearch<E> search;

    public SortedList(Comparator<E> comparator) {
        this.search = new BinarySearch<>(comparator);
    }

    public static <E extends Comparable<E>> SortedList<E> create() {
        return new SortedList<>(E::compareTo);
    }

    @Override
    public void add(E element) {
        this.list.insert(this.search.binarySearchIterative(list, element), element);
    }

    @Override
    public E min() {
        return this.list.get(0);
    }

    @Override
    public int size() {
        return this.list.size();
    }

    @Override
    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    @Override
    public E remove() {
        return this.list.remove(0);
    }
}
