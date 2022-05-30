package codeforces;

import java.util.Scanner;

public class P1553D {
    public static void main(String[] args) {
        try (final var sc = new Scanner(System.in)) {
            final int q = sc.nextInt();
            for (int i = 0; i < q; i++) {
                final String s = sc.next();
                final String t = sc.next();
                if (compare(s, t)) {
                    System.out.println("YES");
                } else {
                    System.out.println("NO");
                }
            }
        }
    }

    private static boolean compare(final String s, final String t) {
        final char[] ss = s.toCharArray();
        final char[] tt = t.toCharArray();
        int i = ss.length - 1;
        int j = tt.length - 1;
        while (i >= j && j >= 0) {
            if (ss[i] == tt[j]) {
                i -= 1;
                j -= 1;
            } else {
                i -= 2;
            }
        }
        return j < 0;
    }
}
