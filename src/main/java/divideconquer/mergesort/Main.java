package com.da.dividiVencera.mergesort;

public class Main {
    public static void main(String[] args) {
        Integer[] arr = {8, 6, 1, 2, 3, 5, 4, 7, 9, 13, 12, 16, 18, 14, 22, 26, 34, 59, 31, 29, 28, 74, 26, 81, 34, 91, 26};
        for (int i = 0; i < arr.length - 1; i++) {
            int dff = arr[i].compareTo(arr[i + 1]);
            assert dff < 0;
        }
    }
}
