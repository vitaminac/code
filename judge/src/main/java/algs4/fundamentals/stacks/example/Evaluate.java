package algs4.fundamentals.stacks.example;

import java.util.StringTokenizer;

import algs4.stdlib.StdIn;
import core.stack.Stack;

public class Evaluate {
    public static void main(String[] args) {
        while (!StdIn.isEmpty()) {
            StringTokenizer tk = new StringTokenizer(StdIn.readLine());
            Stack<String> ops = Stack.fromSinglyLinkedListDoubleReference();
            Stack<Double> values = Stack.fromSinglyLinkedListDoubleReference();
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
