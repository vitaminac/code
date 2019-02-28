package code.adt;

import java.util.Scanner;

public class BagTest {
    public static void main(String[] args) {
        Bag<Double> numbers = new LinkedList<>();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextInt()) {
            numbers.add(scanner.nextDouble());
        }

        int n = numbers.size();

        // compute sample mean
        double sum = 0.0;
        for (double x : numbers)
            sum += x;
        double mean = sum / n;

        // compute sample standard deviation
        sum = 0.0;
        for (double x : numbers) {
            sum += (x - mean) * (x - mean);
        }

        double stddev = Math.sqrt(sum / (n - 1));

        System.out.printf("Mean:    %.2f\n", mean);
        System.out.printf("Std dev: %.2f\n", stddev);
    }
}