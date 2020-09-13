package codeforces;

import java.util.Scanner;

public class P1400B {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            for (int t = sc.nextInt(); t > 0; t--) {
                int p = sc.nextInt();
                int f = sc.nextInt();
                int cnts = sc.nextInt();
                int cntw = sc.nextInt();
                int s = sc.nextInt();
                int w = sc.nextInt();
                // swap s and w when w < s
                if (w < s) {
                    int tmp = s;
                    s = w;
                    w = tmp;
                    tmp = cnts;
                    cnts = cntw;
                    cntw = tmp;
                }
                int max = Integer.MIN_VALUE;
                for (int nps = Math.min(cnts, p / s); nps >= 0; nps--) {
                    int npw = Math.min(cntw, (p - nps * s) / w);
                    int nfs = Math.min(cnts - nps, f / s);
                    int nfw = Math.min(cntw - npw, (f - nfs * s) / w);
                    max = Math.max(max, nps + npw + nfs + nfw);
                }
                System.out.println(max);
            }
        }
    }
}
