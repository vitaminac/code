package code.adt.stack;

import code.adt.BoundedStack;
import code.adt.Stack;

import java.util.Scanner;

public class BoundedStackTest {
    public static void main(String[] args) {
        Stack<String> stack = new BoundedStack<>(5);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            stack.push(scanner.next());
        }
        for (String str : stack) {
            System.out.println(str);
        }
    }
}