package code.algorithm;

import java.util.Scanner;

public class Sattolo {
    public static void cycle(Object[] a) {
        int n = a.length;
        for (int i = n; i > 1; i--) {
            // choose index uniformly in [0, i-1)
            int r = (int) (Math.random() * (i - 1));
            Object swap = a[r];
            a[r] = a[i - 1];
            a[i - 1] = swap;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        String[] strings = new String[n];

        for (int i = 0; i < n; i++) {
            strings[i] = scanner.next();
        }

        // shuffle the array
        Sattolo.cycle(strings);

        // print results.
        for (int i = 0; i < strings.length; i++)
            System.out.println(strings[i]);
    }
}
