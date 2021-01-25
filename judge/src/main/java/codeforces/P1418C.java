package codeforces;

import java.util.Scanner;

public class P1418C {
    private static final int MAX_VALUE = 200000;

    public static void main(String[] args) {
        try (final var sc = new Scanner(System.in)) {
            for (int t = sc.nextInt(); t > 0; t--) {
                int n = sc.nextInt();
                int skipPointsWhenKillOneBossByMePreviously = MAX_VALUE;
                int skipPointsWhenKillTwoBossByMePreviously = MAX_VALUE;
                int skipPointsWhenKillOneBossByMyFriendPreviously = sc.nextInt();
                int skipPointsWhenKillTwoBossByMyFriendPreviously = MAX_VALUE;
                for (; n > 1; n--) {
                    int boss = sc.nextInt();
                    int skipPointsWhenKillOneBossByMeCurrently = Math.min(skipPointsWhenKillOneBossByMyFriendPreviously, skipPointsWhenKillTwoBossByMyFriendPreviously);
                    int skipPointsWhenKillTwoBossByMeCurrently = skipPointsWhenKillOneBossByMePreviously;
                    int skipPointsWhenKillOneBossByMyFriendCurrently = Math.min(skipPointsWhenKillOneBossByMePreviously, skipPointsWhenKillTwoBossByMePreviously) + boss;
                    int skipPointsWhenKillTwoBossByMyFriendCurrently = skipPointsWhenKillOneBossByMyFriendPreviously + boss;
                    skipPointsWhenKillOneBossByMePreviously = skipPointsWhenKillOneBossByMeCurrently;
                    skipPointsWhenKillTwoBossByMePreviously = skipPointsWhenKillTwoBossByMeCurrently;
                    skipPointsWhenKillOneBossByMyFriendPreviously = skipPointsWhenKillOneBossByMyFriendCurrently;
                    skipPointsWhenKillTwoBossByMyFriendPreviously = skipPointsWhenKillTwoBossByMyFriendCurrently;
                }
                System.out.println(Math.min(Math.min(Math.min(skipPointsWhenKillOneBossByMePreviously, skipPointsWhenKillTwoBossByMePreviously), skipPointsWhenKillOneBossByMyFriendPreviously), skipPointsWhenKillTwoBossByMyFriendPreviously));
            }
        }
    }
}
