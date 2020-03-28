package codeforces;

import java.util.Scanner;

public class P630I {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int n = sc.nextInt();
            int parking_lot = (2 * n - 2);
            long result = 0;
            long combination = (long) Math.pow(4, parking_lot - n - 1);
            // when beautiful streak starts at the beginning or at the end
            result += 2 * 3 * combination * 4;
            // when beautiful streak is in the middle
            result += 3 * 3 * combination * (parking_lot - n - 1);
            System.out.print(result);
        }
    }
}
