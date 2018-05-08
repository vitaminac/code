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
        int i = left;
        int j = mid;
        int k = left;
        while (k < right) {
            if (i >= mid) {
                tmp[k++] = arr[j++];
            } else if (j >= right) {
                tmp[k++] = arr[i++];
            } else if (comparator.compare(arr[i], arr[j]) < 0) {
                tmp[k++] = arr[i++];
            } else {
                tmp[k++] = arr[j++];
            }
        }
        // copy back to the array
        for (int idx = left; idx < right; idx++) {
            arr[idx] = tmp[idx];
        }
    }

    private void sort(E arr[], int left, int right, E[] tmp) {
        int mid = (left + right) / 2;
        if (mid <= left) {
            return;
        }
        sort(arr, left, mid, tmp);
        sort(arr, mid, right, tmp);
        merge(arr, left, mid, right, tmp);
    }

    public void sort(E arr[]) {
        sort(arr, 0, arr.length, (E[]) new Object[arr.length]);
    }
}
