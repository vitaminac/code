package code.algorithm;

import java.util.Scanner;

import static code.algorithm.Statistic.binomialCDFIterative;
import static code.algorithm.Statistic.binomialCDFRecursive;

public class BinomialTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextInt()) {
            int N = scanner.nextInt();
            int k = scanner.nextInt();
            double p = scanner.nextDouble();
            System.out.println(binomialCDFRecursive(N, k, p));
            System.out.println(binomialCDFIterative(N, k, p));
        }
    }
}