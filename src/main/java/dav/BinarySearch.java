package dav;

import java.util.Comparator;

public class BinarySearch<E> {
    private final Comparator<? super E> comparator;

    public BinarySearch(Comparator<? super E> comparator) {
        this.comparator = comparator;
    }

    public int binarySearch(E[] inputArr, E key) {
        return binarySearchRec(inputArr, key, 0, inputArr.length - 1);
    }

    private int binarySearchRec(E[] inputArr, E key, int start, int end) {
        int mid = (start + end) / 2;
        E midValue = inputArr[mid];
        if (start > end) {
            return -1;
        } else {
            int dff = this.comparator.compare(key, midValue);
            if (dff == 0) {
                return mid;
            } else if (dff > 0) {
                return binarySearchRec(inputArr, key, mid + 1, end);
            } else {
                return binarySearchRec(inputArr, key, start, mid - 1);
            }
        }
    }

    public static <T extends Comparable<? super T>> BinarySearch<T> create() {
        return new BinarySearch<>(T::compareTo);
    }
}
