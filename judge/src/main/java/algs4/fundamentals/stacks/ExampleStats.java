package algs4.fundamentals.stacks;

import core.map.SeparateChainingHashTableMap;
import core.set.MutableSet;

import java.util.Scanner;

public class ExampleStats {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            MutableSet<Double> numbers = MutableSet.fromMap(SeparateChainingHashTableMap::new);

            while (sc.hasNextInt()) {
                numbers.add(sc.nextDouble());
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

            double stddev = java.lang.Math.sqrt(sum / (n - 1));

            System.out.printf("Mean:    %.2f\n", mean);
            System.out.printf("Std dev: %.2f\n", stddev);
        }
    }
}