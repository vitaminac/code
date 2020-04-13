package algs4.fundamentals.stacks;

import core.DoublyLinkedList;
import core.Stack;

import java.util.Scanner;
import java.util.StringTokenizer;

public class ExampleEvaluate {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            while (sc.hasNext()) {
                StringTokenizer tk = new StringTokenizer(sc.nextLine());
                Stack<String> ops = new DoublyLinkedList<>();
                Stack<Double> values = new DoublyLinkedList<>();
                while (tk.hasMoreTokens()) {
                    String token = tk.nextToken();
                    if (token.equals("(")) ;
                    else if (token.equals("+")) ops.push(token);
                    else if (token.equals("-")) ops.push(token);
                    else if (token.equals("*")) ops.push(token);
                    else if (token.equals("/")) ops.push(token);
                    else if (token.equals("sqrt")) ops.push(token);
                    else if (token.equals(")")) {
                        String op = ops.pop();
                        double v = values.pop();
                        if (op.equals("+")) v = values.pop() + v;
                        else if (op.equals("-")) v = values.pop() - v;
                        else if (op.equals("*")) v = values.pop() * v;
                        else if (op.equals("/")) v = values.pop() / v;
                        else if (op.equals("sqrt")) v = Math.sqrt(v);
                        values.push(v);
                    } else values.push(Double.parseDouble(token));
                }
                System.out.println(values.pop());
            }
        }
    }
}
