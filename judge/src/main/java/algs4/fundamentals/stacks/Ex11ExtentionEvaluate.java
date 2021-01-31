package algs4.fundamentals.stacks;

import core.DoublyLinkedList;
import core.stack.Stack;

import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * https://www.geeksforgeeks.org/arithmetic-expression-evalution/
 * https://www.geeksforgeeks.org/expression-evaluation/
 */
public class Ex11ExtentionEvaluate {
    private static double apply(String op, double right, double left) {
        switch (op) {
            case "*":
                return left * right;
            case "/":
                return left / right;
            case "+":
                return left + right;
            case "-":
                return left - right;
            default:
                throw new IllegalArgumentException();
        }
    }

    public static void main(String[] args) {
        Stack<String> ops = Stack.fromDeque(DoublyLinkedList::new);
        Stack<Double> values = Stack.fromDeque(DoublyLinkedList::new);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            StringTokenizer tk = new StringTokenizer(line);
            while (tk.hasMoreTokens()) {
                final String token = tk.nextToken();
                switch (token) {
                    case "(":
                    case "*":
                    case "/":
                        ops.push(token);
                        break;
                    case "+":
                    case "-":
                        if (!ops.isEmpty() && (ops.peek().equals("+") || ops.peek().equals("-"))) {
                            values.push(apply(ops.pop(), values.pop(), values.pop()));
                        }
                        ops.push(token);
                        break;
                    case ")":
                        if (!ops.peek().equals("(")) values.push(apply(ops.pop(), values.pop(), values.pop()));
                        ops.pop();
                        if (!ops.isEmpty() && (ops.peek().equals("*") || ops.peek().equals("/"))) {
                            values.push(apply(ops.pop(), values.pop(), values.pop()));
                        }
                        break;
                    default:
                        values.push(Double.parseDouble(token));
                        if (!ops.isEmpty() && (ops.peek().equals("*") || ops.peek().equals("/"))) {
                            values.push(apply(ops.pop(), values.pop(), values.pop()));
                        }
                }
            }
            if (!ops.isEmpty()) {
                values.push(apply(ops.pop(), values.pop(), values.pop()));
            }
            System.out.println(values.pop());
        }
    }
}
