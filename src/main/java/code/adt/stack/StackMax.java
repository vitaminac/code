package code.adt.stack;

import code.adt.Stack;

import java.util.Scanner;

public class StackMax {
    public static void main(String[] args) {
        Stack<Integer> maximums = new ResizingArrayStack<>();
        Stack<Integer> temp = new ResizingArrayStack<>();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextInt()) {
            int value = scanner.nextInt();
            while (!maximums.isEmpty() && maximums.peek() > value) {
                temp.push(maximums.pop());
            }
            maximums.push(value);
            while (!temp.isEmpty()) {
                maximums.push(temp.pop());
            }
        }
        while (!maximums.isEmpty()) {
            System.out.print(maximums.pop() + " ");
        }
    }
}
