package kickstart;

import java.util.Arrays;
import java.util.Scanner;

/**
 * https://code.google.com/codejam/contest/6304486/dashboard
 */
public class P2017PractiseACountryLeader {
    public static void solve(Scanner scanner) {
        int T = scanner.nextInt();
        var used = new boolean[26];
        for (int i = 0; i < T; i++) {
            int N = scanner.nextInt();
            var leaderName = new char[0];
            int leaderLetters = 0;
            scanner.nextLine(); // skip line ending
            for (int j = 0; j < N; j++) {
                Arrays.fill(used, false);
                var name = scanner.nextLine().toCharArray();
                int letters = 0;
                for (int k = 0; k < name.length; k++) {
                    var index = name[k] - 'A';
                    if (index >= 0 && index <= 26 && !used[index]) {
                        used[index] = true;
                        letters += 1;
                    }
                }
                if (letters > leaderLetters) {
                    leaderName = name;
                    leaderLetters = letters;
                } else if (letters == leaderLetters) {
                    int n = 0, m = 0;
                    while (n < name.length && m < leaderName.length) {
                        int diff = name[n++] - leaderName[m++];
                        if (diff != 0) {
                            if (diff < 0) {
                                leaderName = name;
                            } else {
                                break;
                            }
                        }
                    }
                }
            }
            System.out.printf("Case #%d: %s\n", i + 1, String.valueOf(leaderName));
        }
    }

    public static void main(String[] args) {
        try (
                var scanner = new Scanner(System.in);
        ) {
            solve(scanner);
        }
    }
}
