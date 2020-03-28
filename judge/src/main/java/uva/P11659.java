package uva;

import java.io.PrintWriter;
import java.util.Scanner;

public class P11659 {
    private static int[] saysCorrect = new int[25], saysWrong = new int[25];

    private static int calculateNumOfReliableInformants(int i) {
        int count = 0;
        while (i != 0) {
            if ((i & 1) != 0) ++count;
            i >>= 1;
        }
        return count;
    }

    public static void main(String[] args) {
        try (
                Scanner scanner = new Scanner(System.in);
                PrintWriter writer = new PrintWriter(System.out)
        ) {
            // number of informants
            int N;
            while ((N = scanner.nextInt()) > 0) {
                // number of surveys
                int A = scanner.nextInt();
                for (int i = 0; i < N; i++) saysCorrect[i] = saysWrong[i] = 0;
                for (int i = 0; i < A; i++) {
                    // record each survey
                    int x = scanner.nextInt() - 1;
                    int y = scanner.nextInt();
                    if (y > 0) saysCorrect[x] |= (1 << y - 1);
                    else saysWrong[x] |= (1 << -y - 1);
                }

                final int mask = (1 << N) - 1;
                int max = 0;
                // test all combinations of reliable informants
                for (int reliable = 1; reliable <= mask; reliable++) {
                    int not_reliable = (~reliable) & mask;

                    int numOfReliableInformants = calculateNumOfReliableInformants(reliable);

                    if (numOfReliableInformants < max) continue; // early skip

                    boolean possible = true;

                    for (int j = 0; j < N; j++) {
                        // if j is reliable
                        if ((reliable & (1 << j)) != 0) {
                            if ((
                                    // if j says someone is not reliable then he must be in the list of not_reliable
                                    (not_reliable & saysWrong[j]) != saysWrong[j] ||
                                    // if j says someone is reliable then he must be in the list of reliable
                                    (reliable & saysCorrect[j]) != saysCorrect[j])
                            ) {
                                possible = false;
                                break;
                            }
                        }
                    }

                    if (possible) max = Math.max(max, numOfReliableInformants);
                }
                writer.println(max);
            }
        }
    }
}
