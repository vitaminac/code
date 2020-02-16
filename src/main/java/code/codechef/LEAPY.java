package code.codechef;

import java.util.Scanner;

public class LEAPY {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int T = sc.nextInt();
            for (int i = 0; i < T; i++) {
                int year = sc.nextInt();
                if ((year % 4 == 0) && !((year % 100 == 0) && (year % 400 != 0))) {
                    System.out.println("It is a leap year.");
                } else {
                    do {
                        year = (year + 4) / 4 * 4;
                    } while (!((year % 4 == 0) && !((year % 100 == 0) && (year % 400 != 0))));
                    System.out.println("Not a leap year. Suggested: " + year);
                }
            }
        }
    }
}
