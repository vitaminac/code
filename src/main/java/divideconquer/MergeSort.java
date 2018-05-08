package divideconquer;

import java.util.Comparator;

public class MergeSort<E> {
    public static <T extends Comparable<? super T>> MergeSort<T> create() {
        return new MergeSort<>(T::compareTo);
    }

    private final Comparator<? super E> comparator;

    public MergeSort(Comparator<? super E> comparator) {
        this.comparator = comparator;
    }

    private void merge(E arr[], int left, int mid, int right, E[] tmp) {
        // copy to the tmp array
        for (int idx = left; idx < right; idx++) {
            tmp[idx] = arr[idx];
        }
        for (int idx = left, i = left, j = mid; idx < right; idx++) {
            if (i >= mid) {
                arr[idx] = tmp[j++];
            } else if (j >= right) {
                arr[idx] = tmp[i++];
            } else if (comparator.compare(tmp[i], tmp[j]) < 0) {
                arr[idx] = tmp[i++];
            } else {
                arr[idx] = tmp[j++];
            }
        }
    }

    private void sort(E arr[], int left, int right, E[] tmp) {
        int mid = (left + right) / 2;
        if (mid > left) {
            sort(arr, left, mid, tmp);
            sort(arr, mid, right, tmp);
            merge(arr, left, mid, right, tmp);
        }
    }

    public void sort(E arr[]) {
        sort(arr, 0, arr.length, (E[]) new Object[arr.length]);
    }
}
