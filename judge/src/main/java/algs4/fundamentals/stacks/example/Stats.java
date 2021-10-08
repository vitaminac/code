package algs4.fundamentals.stacks.example;

import algs4.stdlib.StdIn;
import core.list.ArrayList;
import core.behaviour.Bag;

public class Stats {
    public static void main(String[] args) {
        // read in numbers
        Bag<Double> numbers = Bag.fromList(ArrayList::new);
        while (!StdIn.isEmpty()) {
            numbers.add(StdIn.readDouble());
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