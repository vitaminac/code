package code.algorithm.divideandconquer;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class BinarySearch<E> {
    private final Comparator<? super E> comparator;

    public BinarySearch(Comparator<? super E> comparator) {
        this.comparator = comparator;
    }

    public int binarySearch(E[] input, E key) {
        return binarySearchRec(input, key, 0, input.length - 1);
    }

    private int binarySearchRec(E[] input, E key, int start, int end) {
        int mid = (start + end) / 2;
        E mean = input[mid];
        if (start > end) {
            return -1;
        } else {
            int dff = this.comparator.compare(key, mean);
            if (dff == 0) {
                return mid;
            } else if (dff > 0) {
                return binarySearchRec(input, key, mid + 1, end);
            } else {
                return binarySearchRec(input, key, start, mid - 1);
            }
        }
    }

    public static <T extends Comparable<? super T>> BinarySearch<T> get() {
        return new BinarySearch<>(T::compareTo);
    }

    public static int indexOf(int[] a, int key) {
        int lo = 0;
        int hi = a.length - 1;
        while (lo <= hi) {
            // Key is in a[lo..hi] or not present.
            int mid = lo + (hi - lo) / 2;
            if (key < a[mid]) hi = mid - 1;
            else if (key > a[mid]) lo = mid + 1;
            else return mid;
        }
        return -1;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n;
        while ((n = scanner.nextInt()) > 0) {
            int[] integers = new int[n];
            for (int i = 0; i < n; i++) {
                integers[i] = scanner.nextInt();
            }
            Arrays.sort(integers);
            final Integer[] numbers = Arrays.stream(integers).boxed().toArray(Integer[]::new);
            BinarySearch<Integer> search = BinarySearch.get();
            n = scanner.nextInt();
            for (int i = 0; i < n; i++) {
                int key = scanner.nextInt();
                if (search.binarySearch(numbers, key) < 0) {
                    assert indexOf(integers, key) < 0;
                    System.out.println(key);
                }
            }
        }
    }
}
