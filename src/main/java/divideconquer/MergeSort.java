package divideconquer;

import java.util.Comparator;

public class MergeSort<E> {
    private final Comparator<? super E> comparator;

    public MergeSort(Comparator<? super E> comparator) {
        this.comparator = comparator;
    }

    public static <T extends Comparable<? super T>> MergeSort<T> create() {
        return new MergeSort<>(T::compareTo);
    }

    private void merge(E arr[], int head, int mid, int tail, E[] ordered) {
        int i = head;
        int j = mid;
        int k = head;
        while (k < tail) {
            if (i >= mid) {
                arr[k++] = ordered[j++];
            } else if (j >= tail) {
                arr[k++] = ordered[i++];
            } else if (comparator.compare(ordered[i], ordered[j]) < 0) {
                arr[k++] = ordered[i++];
            } else {
                arr[k++] = ordered[j++];
            }
        }
    }

    private void mergeSort(E arr[], int head, int tail, E[] ordered) {
        int mid = (head + tail) / 2;
        if (mid <= head) {
            return;
        }
        mergeSort(arr, head, mid, ordered);
        mergeSort(arr, mid, tail, ordered);
        merge(ordered, head, mid, tail, arr);
        for (int i = head; i < tail; i++) {
            arr[i] = ordered[i];
        }
    }

    public void mergeSort(E arr[]) {
        mergeSort(arr, 0, arr.length, (E[]) new Object[arr.length]);
    }
}
