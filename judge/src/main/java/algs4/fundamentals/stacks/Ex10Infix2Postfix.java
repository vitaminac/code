package algs4.fundamentals.stacks;

import core.Stack;

import java.util.PrimitiveIterator;
import java.util.Scanner;

/**
 * 10. Write a program to convert an infix expression to postfix.
 * Scan the infix expression from left to right.
 */
public class Ex10Infix2Postfix {
    private static final int precedence[] = new int[128];

    static {
        precedence['+'] = 1;
        precedence['-'] = 1;
        precedence['*'] = 2;
        precedence['/'] = 2;
    }

    public static void main(String[] args) {
        try (var scanner = new Scanner(System.in)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                PrimitiveIterator.OfInt it = line.chars().iterator();
                Stack<Character> stack = Stack.fromSinglyLinkedListDoubleReference();
                StringBuilder sb = new StringBuilder();
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
                        if (precedence[c] > 0) { // if c is operator
                            if (stack.isEmpty()) {
                                stack.push(c);
                            } else {
                                if (precedence[c] > precedence[stack.peek()]) {
                                    // Operator with high precedence than top of stack: push onto stack.
                                    stack.push(c);
                                } else {
                                    // Operator with lower or equal precedence than top of stack:
                                    // repeatedly pop elements from the stack
                                    // and output them until top of stack has higher precedence.
                                    // Push the scanned operator onto the stack.
                                    do {
                                        sb.append(stack.pop());
                                    } while (!stack.isEmpty() && precedence[c] <= precedence[stack.peek()]);
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
                System.out.println(sb.toString());
            }
        }
    }
}
