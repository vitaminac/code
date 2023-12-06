package algs4.fundamentals.stacks.web;

import java.util.PrimitiveIterator;
import java.util.Scanner;

import collections.linkedlist.SinglyLinkedListDoubleReference;
import collections.stack.Stack;

public class WebEx15InfixToPostfix {
    private static final int PRECEDENCE[] = new int[128];

    static {
        PRECEDENCE['+'] = 1;
        PRECEDENCE['-'] = 1;
        PRECEDENCE['*'] = 2;
        PRECEDENCE['/'] = 2;
    }

    private static String infix2Postfix(final String infix) {
        StringBuilder sb = new StringBuilder();
        PrimitiveIterator.OfInt it = infix.chars().iterator();
        Stack<Character> stack = Stack.fromSteque(SinglyLinkedListDoubleReference::new);
        while (it.hasNext()) {
            char c = (char) (int) it.next();
            if (c == '(') {
                // Left parentheses: push onto stack.
                stack.push(c);
            } else if (c == ')') {
                // Right parentheses: repeatedly pop elements from the stack
                // and output them until a left parenthesis is encountered.
                // Discard both parentheses.
                while ((c = stack.pop()) != '(') {
                    sb.append(c);
                }
            } else {
                if (PRECEDENCE[c] > 0) { // if c is operator
                    if (stack.isEmpty()) {
                        stack.push(c);
                    } else {
                        if (PRECEDENCE[c] > PRECEDENCE[stack.peek()]) {
                            // Operator with high precedence than top of stack: push onto stack.
                            stack.push(c);
                        } else {
                            // Operator with lower or equal precedence than top of stack:
                            // repeatedly pop elements from the stack
                            // and output them until top of stack has higher precedence.
                            // Push the scanned operator onto the stack.
                            do {
                                sb.append(stack.pop());
                            } while (!stack.isEmpty() && PRECEDENCE[c] <= PRECEDENCE[stack.peek()]);
                            stack.push(c);
                        }
                    }
                } else {
                    // Operand: output it.
                    sb.append(c);
                }
            }
        }
        // Afterward, pop remaining elements off stack and output them.
        while (!stack.isEmpty()) {
            sb.append(stack.pop());
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        try (var scanner = new Scanner(System.in)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                System.out.println(infix2Postfix(line));
            }
        }
    }
}
