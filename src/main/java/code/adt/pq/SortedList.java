package code.adt.pq;

import code.adt.ArrayList;
import code.algorithm.divideandconquer.BinarySearch;

import java.util.Comparator;
import java.util.Iterator;

public class SortedList<E> implements PriorityQueue<E> {
    private final ArrayList<E> list = new ArrayList<>();
    private final BinarySearch<E> search;

    public SortedList(Comparator<E> comparator) {
        this.search = new BinarySearch<>(comparator);
    }

    public static <E extends Comparable<E>> SortedList<E> create() {
        return new SortedList<>(E::compareTo);
    }

    @Override
    public void add(E element) {
        this.list.add(this.search.binarySearchIterative(list, element), element);
    }

    @Override
    public E peek() {
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
        return this.list.removeFirst();
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int idx = 0;

            @Override
            public boolean hasNext() {
                return idx < SortedList.this.list.size();
            }

            @Override
            public E next() {
                return SortedList.this.list.get(this.idx++);
            }
        };
    }
}
