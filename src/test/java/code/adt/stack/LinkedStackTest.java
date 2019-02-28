package code.adt.stack;

import code.adt.LinkedList;
import code.adt.Stack;

import java.util.Scanner;

public class LinkedStackTest {
    public static void main(String[] args) {
        Stack<String> stack = new LinkedList<>();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String item = scanner.next();
            if (!item.equals("-"))
                stack.push(item);
            else if (!stack.isEmpty())
                System.out.print(stack.pop() + " ");
        }
        System.out.println("(" + stack.size() + " left on stack)");
    }
}