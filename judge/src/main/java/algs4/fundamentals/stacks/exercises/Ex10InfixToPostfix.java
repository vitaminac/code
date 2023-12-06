package algs4.fundamentals.stacks.exercises;

import java.util.StringTokenizer;

import algs4.stdlib.StdIn;
import algs4.stdlib.StdOut;
import collections.linkedlist.SinglyLinkedListDoubleReference;
import collections.stack.Stack;

/**
 * https://algs4.cs.princeton.edu/13stacks/InfixToPostfix.java.html
 */
public class Ex10InfixToPostfix {
    public static void main(String[] args) {
        Stack<String> stack = Stack.fromSteque(SinglyLinkedListDoubleReference::new);
        while (!StdIn.isEmpty()) {
            final var tokenizer = new StringTokenizer(StdIn.readLine());
            while (tokenizer.hasMoreElements()) {
                final var token = tokenizer.nextToken();
                if (token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/")) stack.push(token);
                else if (token.equals(")")) StdOut.print(stack.pop() + " ");
                else if (token.equals("(")) ;
                else StdOut.print(token + " ");
            }
            StdOut.println();
        }
    }
}
