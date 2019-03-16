package code.adt.stack;

import code.adt.ArrayList;
import code.adt.Stack;

import java.util.Scanner;

public class ResizingArrayStack {
    public static void main(String[] args) {
        Stack<String> stack = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String item = scanner.next();
            if (!item.equals("-")) stack.push(item);
            else if (!stack.isEmpty()) System.out.print(stack.pop() + " ");
        }
        System.out.println("(" + stack.size() + " left on stack)");
    }
}