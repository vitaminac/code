package code.adt.stack;

import code.adt.LinkedList;
import code.adt.Stack;

import java.util.Scanner;

public class StackMax {
    public static void main(String[] args) {
        Stack<Integer> maximums = new LinkedList<>();
        Stack<Integer> temp = new LinkedList<>();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextInt()) {
            int value = scanner.nextInt();
            while (!maximums.isEmpty() && maximums.top() > value) {
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
