package code.algorithm;

import java.util.Scanner;

public class SattoloTest {
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