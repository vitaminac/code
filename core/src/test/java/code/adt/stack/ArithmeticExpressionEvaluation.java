package code.adt.stack;

import code.adt.DoublyLinkedList;
import code.adt.Stack;

import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * https://www.geeksforgeeks.org/arithmetic-expression-evalution/
 * https://www.geeksforgeeks.org/expression-evaluation/
 */
public class ArithmeticExpressionEvaluation {
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
        Stack<String> ops = new DoublyLinkedList<>();
        Stack<Double> values = new DoublyLinkedList<>();
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
                        if (!ops.isEmpty() && (ops.top().equals("+") || ops.top().equals("-"))) {
                            values.push(apply(ops.pop(), values.pop(), values.pop()));
                        }
                        ops.push(token);
                        break;
                    case ")":
                        if (!ops.top().equals("(")) values.push(apply(ops.pop(), values.pop(), values.pop()));
                        ops.pop();
                        if (!ops.isEmpty() && (ops.top().equals("*") || ops.top().equals("/"))) {
                            values.push(apply(ops.pop(), values.pop(), values.pop()));
                        }
                        break;
                    default:
                        values.push(Double.parseDouble(token));
                        if (!ops.isEmpty() && (ops.top().equals("*") || ops.top().equals("/"))) {
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
