package code.algorithm;

import java.util.Scanner;

import static code.algorithm.Binomial.binomialIterative;
import static code.algorithm.Binomial.binomialRecursive;

public class BinomialTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextInt()) {
            int N = scanner.nextInt();
            int k = scanner.nextInt();
            double p = scanner.nextDouble();
            System.out.println(binomialRecursive(N, k, p));
            System.out.println(binomialIterative(N, k, p));
        }
    }
}