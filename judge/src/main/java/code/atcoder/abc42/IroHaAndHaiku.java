package code.atcoder.abc42;

import java.util.Scanner;

public class IroHaAndHaiku {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int A = sc.nextInt();
            int B = sc.nextInt();
            int C = sc.nextInt();
            System.out.println((
                    (A == 5 && B == 5 && C == 7) || (A == 5 && B == 7 && C == 5) || (A == 7 && B == 5 && C == 5)
            ) ? "YES" : "NO");
        }
    }
}
