package divideconquer;

import java.util.Comparator;

public class QuickSort<E> {
    public static <T extends Comparable<? super T>> QuickSort<T> create() {
        return new QuickSort<>(T::compareTo);
    }

    private final Comparator<? super E> comparator;

    public QuickSort(Comparator<? super E> comparator) {
        this.comparator = comparator;
    }

    public void sort(E[] array) {
        this.sort(array, 0, array.length - 1);
    }

    private void sort(E[] array, int head, int pivot) {
        if (head < pivot) {
            int left = head;
            int right = pivot - 1;
            E tmp;
            while (right >= left) {
                if (this.comparator.compare(array[left], array[pivot]) > 0) {
                    tmp = array[right];
                    array[right--] = array[left];
                    array[left] = tmp;
                } else {
                    left += 1;
                }
            }
            tmp = array[left];
            array[left] = array[pivot];
            array[pivot] = tmp;
            sort(array, head, right);
            sort(array, left + 1, pivot);
        }
    }
}
