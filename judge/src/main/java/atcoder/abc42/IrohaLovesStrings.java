package atcoder.abc42;

import java.util.Arrays;
import java.util.Scanner;

public class IrohaLovesStrings {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int N = sc.nextInt();
            int L = sc.nextInt();
            String[] strings = new String[N];
            for (int i = 0; i < N; i++) {
                strings[i] = sc.next();
            }
            Arrays.sort(strings);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < N; i++) {
                sb.append(strings[i]);
            }
            System.out.println(sb.toString());
        }
    }
}
