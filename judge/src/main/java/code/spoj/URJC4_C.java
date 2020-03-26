package code.spoj;

import java.util.Scanner;

public class URJC4_C {
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        int num = in.nextInt();
        for (int i = 0; i < num; i++) {
            int year = in.nextInt();
            do {
                year = (year + 4) / 4 * 4;
            } while (!((year % 4 == 0) && !((year % 100 == 0) && (year % 400 != 0))));
            System.out.println(year);
        }
    }
}
