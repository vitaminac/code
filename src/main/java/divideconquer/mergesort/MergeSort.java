package com.da.dividiVencera.mergesort;

import java.util.Comparator;

public class MergeSort<E> {
    private final Comparator<? super E> comparator;

    public MergeSort(Comparator<? super E> comparator) {
        this.comparator = comparator;
    }

    private void merge(E arr[], int head, int mid, int tail) {

    }

    private void mergesort(E arr[], int head, int tail, E[] backup) {
        if ((tail - head) <= 1) {
            return;
        }
        int mid = (head + tail) / 2;
        mergesort(arr, head, mid - 1, backup);
        mergesort(arr, mid, tail, backup);
        merge(arr, head, mid, tail);
    }

    public void mergesort(E arr[]) {
        mergesort(arr, 0, arr.length - 1, (E[]) new Object[arr.length]);
    }

    public static <T extends Comparable<? super T>> MergeSort<T> create() {
        return new MergeSort<>(T::compareTo);
    }
}
