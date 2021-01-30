package core;

import java.util.Comparator;
import java.util.function.Consumer;

public final class Arrays {

    public static <E> E[] copyFrom(E[] arr, int from, int length) {
        @SuppressWarnings("unchecked") E[] clone = (E[]) new Object[length];
        for (int i = from + length - 1; i >= from; i--) clone[i] = arr[i];
        return clone;
    }

    public static <E> void fill(E[] arr, E value) {
        for (int i = 0; i < arr.length; i++) arr[i] = value;
    }

    public static <E> void copyTo(E[] source, E[] destination, int from, int to) {
        for (int i = from; i <= to; i++) destination[i] = source[i];
    }

    /**
     * Returns the left-most index of the specified key in a sorted array.
     *
     * @param arr
     * @param key
     * @return the position of key, negative number if not found
     */
    public static <E> int binarySearch(E[] arr, E key, int low, int high, Comparator<? super E> comparator) {
        int diff = -1;
        while (low <= high) {
            int mid = low + ((high - low) / 2);
            diff = comparator.compare(key, arr[mid]);
            if (diff <= 0) high = mid - 1;
            else low = mid + 1;
        }
        if (diff == 0) return low; // key found
        else return -low - 1;  // key not found
    }

    public static <E> int binarySearch(E[] arr, E key, Comparator<? super E> comparator) {
        return binarySearch(arr, key, 0, arr.length - 1, comparator);
    }

    public static <E extends Comparable<? super E>> int binarySearch(E[] arr, E key, int low, int high) {
        return binarySearch(arr, key, low, high, E::compareTo);
    }

    public static <E extends Comparable<? super E>> int binarySearch(E[] arr, E key) {
        return binarySearch(arr, key, E::compareTo);
    }

    public static <E> void insertion_sort(E[] arr, int low, int high, Comparator<? super E> comparator) {
        // Initialization: arr[low...low] consist of just the single element, moreover the sub-array is sorted.
        for (int i = low + 1; i <= high; i++) {
            final E key = arr[i];
            // Maintenance: arr[low...i-1] is sorted,
            // for arr[i] we move one by one position to the right
            // until it finds the proper position i >= j >= low
            // then the sub-array arr[low...i] is sorted
            int j = i - 1;
            for (; j >= low && comparator.compare(key, arr[j]) < 0; j--) {
                arr[j + 1] = arr[j];
            }
            arr[j + 1] = key;
        }
        // Termination: when i = high + 1 then the array arr[low...high] is sorted
    }

    public static <E> void quick_sort(E[] arr, int low, int high, Comparator<? super E> comparator) {
        if (low >= high) return;
        int left = low;
        int right = high - 1;
        E pivot = arr[high];
        while (right >= left) {
            if (comparator.compare(arr[left], pivot) <= 0) left += 1;
            else {
                E tmp = arr[left];
                arr[left] = arr[right];
                arr[right--] = tmp;
            }
        }
        arr[high] = arr[left];
        arr[left] = pivot;
        quick_sort(arr, low, right, comparator);
        quick_sort(arr, left, high, comparator);
    }

    public static <E extends Comparable<? super E>> void quick_sort(E[] arr, int low, int high) {
        quick_sort(arr, low, high, Comparator.naturalOrder());
    }

    public static <E> void merge(E[] arr, E[] aux, int low, int mid, int high, Comparator<? super E> comparator) {
        int left = low, right = mid + 1;
        for (int i = low; i <= high; i++) {
            if (left > mid) arr[i] = aux[right++];
            else if (right > high) arr[i] = aux[left++];
            else if (comparator.compare(aux[left], aux[right]) <= 0) arr[i] = aux[left++];
            else arr[i] = aux[right++];
        }
    }

    public static <E> void merge_sort(E[] arr, E[] aux, int low, int high, Comparator<? super E> comparator) {
        if (low >= high) return;
        if (high - low <= 8) insertion_sort(arr, low, high, comparator);
        else {
            int mid = low + (high - low) / 2;
            merge_sort(arr, aux, low, mid, comparator);
            merge_sort(arr, aux, mid + 1, high, comparator);
            Arrays.copyTo(arr, aux, low, high);
            merge(arr, aux, low, mid, high, comparator);
        }
    }

    public static <E> List<E> asList(final E[] elements) {
        return new List<E>() {
            @Override
            public int size() {
                return elements.length;
            }

            @Override
            public boolean isEmpty() {
                return elements.length == 0;
            }

            @Override
            public void clear() {
                throw new UnsupportedOperationException();
            }

            @Override
            public void add(int index, E element) {
                throw new UnsupportedOperationException();
            }

            @Override
            public E remove(int index) {
                throw new UnsupportedOperationException();
            }

            @Override
            public int indexOf(E element) {
                for (int i = 0; i < elements.length; i++) {
                    if (elements[i].equals(element)) {
                        return i;
                    }
                }
                return -1;
            }

            @Override
            public int lastIndexOf(E element) {
                for (int i = elements.length - 1; i >= 0; i--) {
                    if (elements[i].equals(element)) {
                        return i;
                    }
                }
                return -1;
            }

            @Override
            public void forEach(Consumer<? super E> consumer) {
                for (var e : elements) consumer.accept(e);
            }

            @Override
            public E get(int index) {
                return elements[index];
            }

            @Override
            public E set(int index, E element) {
                throw new RuntimeException();
            }
        };
    }

    public static <E> String toString(final E[] elements) {
        if (elements == null) return "null";
        if (elements.length == 0) return "[]";
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        sb.append(elements[0]);
        for (int i = 1; i < elements.length; i++) {
            sb.append(',');
            sb.append(' ');
            sb.append(elements[i]);
        }
        sb.append(']');
        return sb.toString();
    }
}
