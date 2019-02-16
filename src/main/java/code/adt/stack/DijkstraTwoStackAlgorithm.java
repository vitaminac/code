package code.adt.stack;

import java.util.Scanner;
import java.util.StringTokenizer;

public class DijkstraTwoStackAlgorithm {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            Stack<String> ops = new LinkedStack<>();
            Stack<Double> vals = new LinkedStack<>();
            String line = scanner.nextLine();
            final StringTokenizer tokenizer = new StringTokenizer(line);
            while (tokenizer.hasMoreTokens()) {
                String s = tokenizer.nextToken();
                if (s.equals("(")) ;
                else if (s.equals("+")) ops.push(s);
                else if (s.equals("-")) ops.push(s);
                else if (s.equals("*")) ops.push(s);
                else if (s.equals("/")) ops.push(s);
                else if (s.equals("sqrt")) ops.push(s);
                else if (s.equals(")")) {
                    String op = ops.pop();
                    double v = vals.pop();
                    if (op.equals("+")) v = vals.pop() + v;
                    else if (op.equals("-")) v = vals.pop() - v;
                    else if (op.equals("*")) v = vals.pop() * v;
                    else if (op.equals("/")) v = vals.pop() / v;
                    else if (op.equals("sqrt")) v = Math.sqrt(v);
                    vals.push(v);
                } else vals.push(Double.parseDouble(s));
            }
            System.out.println(vals.pop());
        }
    }
}
