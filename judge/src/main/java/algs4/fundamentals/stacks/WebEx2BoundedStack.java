package algs4.fundamentals.stacks;

import core.stack.UndoStack;
import core.stack.Stack;

import java.util.Scanner;

public class WebEx2BoundedStack {
    public static void main(String[] args) {
        Stack<String> stack = new UndoStack<>(5);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            stack.push(scanner.next());
        }
        while (!stack.isEmpty()) {
            System.out.println(stack.pop());
        }
    }
}