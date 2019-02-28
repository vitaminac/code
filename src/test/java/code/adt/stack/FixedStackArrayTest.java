package code.adt.stack;

import code.adt.FixedArray;
import code.adt.Stack;

import java.util.Scanner;

public class FixedStackArrayTest {
    public static void main(String[] args) {
        Stack<Integer> stack = new FixedArray<>(5);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextInt()) {
            stack.push(scanner.nextInt());
        }
        for (int i : stack) {
            System.out.println(i);
        }
    }
}