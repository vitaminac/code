package algs4.fundamentals.stacks.creative;

import java.util.Scanner;

import static core.util.Eval.evalAsDouble;

/**
 * https://www.geeksforgeeks.org/arithmetic-expression-evalution/
 * https://www.geeksforgeeks.org/expression-evaluation/
 * https://algs4.cs.princeton.edu/13stacks/EvaluateDeluxe.java.html
 */
public class CreativeProblem51EvaluateDeluxe {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            while (sc.hasNextLine()) {
                System.out.println(evalAsDouble(sc.nextLine()));
            }
        }
    }
}
