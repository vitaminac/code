package code.algorithm.divideandconquer;

import code.adt.ArrayList;

import java.util.Comparator;

public class BinarySearch<E> {
    private final Comparator<? super E> comparator;

    public BinarySearch(Comparator<? super E> comparator) {
        this.comparator = comparator;
    }

    public int binarySearchRecursive(E[] array, E key) {
        return binarySearchRecursive(array, key, 0, array.length - 1);
    }

    private int binarySearchRecursive(E[] array, E key, int start, int end) {
        int mid = (start + end) / 2;
        E mean = array[mid];
        if (start > end) {
            return -1;
        } else {
            int dff = this.comparator.compare(key, mean);
            if (dff == 0) {
                return mid;
            } else if (dff > 0) {
                return binarySearchRecursive(array, key, mid + 1, end);
            } else {
                return binarySearchRecursive(array, key, start, mid - 1);
            }
        }
    }

    public int binarySearchIterative(E[] array, E key) {
        int lo = 0;
        int hi = array.length - 1;
        while (lo <= hi) {
            // Key is in a[lo..hi] or not present.
            int mid = lo + (hi - lo) / 2;
            int diff = this.comparator.compare(key, array[mid]);
            if (diff < 0) hi = mid - 1;
            else if (diff > 0) lo = mid + 1;
            else return mid;
        }
        return -1;
    }

    public int binarySearchIterative(ArrayList<E> list, E key) {
        int low = 0;
        int high = list.size() - 1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            int diff = this.comparator.compare(list.get(mid), key);
            if (diff < 0) low = mid + 1;
            else if (diff > 0) high = mid - 1;
            else return mid; // key found
        }
        return low;  // key not found
    }

    public static <T extends Comparable<? super T>> BinarySearch<T> get() {
        return new BinarySearch<>(T::compareTo);
    }
}
