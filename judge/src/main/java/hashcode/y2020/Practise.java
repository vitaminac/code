package hashcode.y2020;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

public class Practise {
    public static void main(String[] args) throws IOException {
        String[] inputs = new String[]{
                "a_example.in",
                "b_small.in",
                "c_medium.in",
                "d_quite_big.in",
                "e_also_big.in"
        };
        for (String input : inputs) {
            try (
                    Scanner sc = new Scanner(new BufferedReader(new FileReader(input)));
                    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(input + ".out")))
            ) {
                int M = sc.nextInt();
                int N = sc.nextInt();
                int[] pizzas = new int[N];
                for (int i = 0; i < N; i++) {
                    pizzas[i] = sc.nextInt();
                }
                int sum = 0;
                Deque<Integer> dq = new ArrayDeque<>();
                for (int i = N - 1; i >= 0; i--) {
                    if (sum + pizzas[i] <= M) {
                        sum += pizzas[i];
                        dq.addFirst(i);
                    }
                }
                pw.println(dq.size());
                for (int n : dq) {
                    pw.print(n);
                    pw.print(' ');
                }
                pw.flush();
            }
        }
    }
}
