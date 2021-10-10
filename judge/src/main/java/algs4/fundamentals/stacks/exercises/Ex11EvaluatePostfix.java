package algs4.fundamentals.stacks.exercises;

import java.util.StringTokenizer;

import algs4.stdlib.StdIn;
import algs4.stdlib.StdOut;
import core.stack.Stack;

/**
 * https://algs4.cs.princeton.edu/13stacks/EvaluatePostfix.java.html
 */
public class Ex11EvaluatePostfix {
    public static void main(String[] args) {
        Stack<Integer> stack = Stack.fromSinglyLinkedListDoubleReference();

        while (!StdIn.isEmpty()) {
            final var tokenizer = new StringTokenizer(StdIn.readLine());
            while (tokenizer.hasMoreTokens()) {
                final var token = tokenizer.nextToken();
                if (token.equals("+")) stack.push(stack.pop() + stack.pop());
                else if (token.equals("*")) stack.push(stack.pop() * stack.pop());
                else if (token.equals("-")) stack.push(stack.pop() - stack.pop());
                else if (token.equals("/")) stack.push(stack.pop() / stack.pop());
                else stack.push(Integer.parseInt(token));
            }
            StdOut.println(stack.pop());
        }
    }
}
