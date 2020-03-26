package code.concurso;

import java.util.Arrays;
import java.util.Scanner;

public class MiniJuegoGracioso {
    private static class Item implements Comparable<Item> {
        private double distance;
        private String song;

        @Override
        public int compareTo(Item o) {
            return this.distance > o.distance ? 1 : -1;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        for (int i = 0; i < n; i++) {
            int k = scanner.nextInt();
            Item[] items = new Item[k];
            for (int j = 0; j < k; j++) {
                items[j] = new Item();
                items[j].distance = scanner.nextDouble();
                items[j].song = scanner.next();
            }
            Arrays.sort(items);
            int m = scanner.nextInt();
            for (int j = 0; j < m; j++) {
                double distance = scanner.nextDouble();
                System.out.println(items[find(items, 0, items.length - 1, distance)].song);
            }
        }
    }

    private static int find(Item[] arr, int start, int end, double distance) {
        int mid = (start + end) / 2;
        if (mid == start) {
            return mid;
        } else if (arr[mid].distance > distance) {
            if (arr[mid - 1].distance > distance) {
                return find(arr, start, mid - 1, distance);
            } else {
                return mid;
            }
        } else {
            return find(arr, mid + 1, end, distance);
        }
    }
}
