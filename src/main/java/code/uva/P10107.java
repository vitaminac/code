package code.uva;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=1048
public class P10107 {
    public static void main(String[] args) {
        try (
                Scanner scanner = new Scanner(System.in);
                PrintWriter writer = new PrintWriter(System.out)
        ) {
            int[] numbers = new int[10000];
            int n = 0;
            while (scanner.hasNext()) {
                numbers[n++] = scanner.nextInt();
                Arrays.sort(numbers, 0, n);
                int idx = n / 2;
                if (n % 2 == 0) {
                    writer.write((numbers[idx] + numbers[idx - 1]) / 2 + "\n");
                } else {
                    writer.write(numbers[idx] + "\n");
                }
            }
        }
    }
}
