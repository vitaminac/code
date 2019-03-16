package code.algorithm.divideandconquer;

import java.util.Arrays;
import java.util.Scanner;

public class BinarySearchTest {
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
                if (search.binarySearchRecursive(numbers, key) < 0 && (search.binarySearchIterative(numbers, key) == integers.length || integers[search.binarySearchIterative(numbers, key)] != key)) {
                    System.out.println(key);
                }
            }
        }
    }
}