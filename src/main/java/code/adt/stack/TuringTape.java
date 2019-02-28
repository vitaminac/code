package code.adt.stack;

import code.adt.Stack;

import java.util.Scanner;

public class TuringTape {
    public static void main(String[] args) {
        Stack<Integer> left = new ResizingArrayStack<>();
        Stack<Integer> right = new ResizingArrayStack<>();
        int active = 0;

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String token = scanner.next();
            if (token.equals("moveLeft")) {
                right.push(active);
                if (left.isEmpty()) {
                    active = 0;
                } else {
                    active = left.pop();
                }
            } else if (token.equals("moveRight")) {
                left.push(active);
                if (right.isEmpty()) {
                    active = 0;
                } else {
                    active = right.pop();
                }
            } else if (token.equals("look")) {
                System.out.println(active);
            } else if (token.equals("write")) {
                active = scanner.nextInt();
            }
        }
    }
}
