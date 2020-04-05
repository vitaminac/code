package aceptaelreto;

import java.util.*;

public class P555FugaDeAlcatraz {
    private static class Guard {
        private final int x;
        private final int y;

        private Guard(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private static final Comparator<Guard> comparator = new Comparator<Guard>() {
        @Override
        public int compare(Guard g1, Guard g2) {
            if (g1.x == g2.x) return g1.y - g2.y;
            else return g1.x - g2.x;
        }
    };

    private static boolean canReach(Guard g1, Guard g2) {
        int diff_x = g1.x - g2.x;
        int diff_y = g1.y - g2.y;
        return diff_x * diff_x + diff_y * diff_y <= 400;
    }

    private static boolean canReachEnd(int i, int end, boolean[] visited, boolean[] canReach, Guard[] guards) {
        if (!visited[i]) {
            visited[i] = true;
            if (end - guards[i].x <= 10) {
                canReach[i] = true;
            } else {
                for (int prev = i - 1; prev >= 0 && !canReach[i] && guards[i].x - guards[prev].x <= 20; prev--) {
                    if (canReach(guards[i], guards[prev])) {
                        canReach[i] = canReachEnd(prev, end, visited, canReach, guards);
                    }
                }
                for (int next = i + 1; next < guards.length && !canReach[i] && guards[next].x - guards[i].x <= 20; next++) {
                    if (canReach(guards[i], guards[next])) {
                        canReach[i] = canReachEnd(next, end, visited, canReach, guards);
                    }
                }
            }
        }
        return canReach[i];
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int width, height;
            while ((width = sc.nextInt()) != 0) {
                height = sc.nextInt();

                int N = sc.nextInt();
                final Guard[] guards = new Guard[N];

                for (int i = 0; i < N; i++) guards[i] = new Guard(sc.nextInt(), sc.nextInt());

                Arrays.sort(guards, comparator);

                boolean blocked = false;
                Map<Integer, Boolean> memo = new HashMap<>();
                for (int i = 0; i < N && !blocked && guards[i].x <= 10; i++) {
                    blocked = canReachEnd(i, width, new boolean[N], new boolean[N], guards);
                }
                System.out.println(blocked ? "NO" : "SI");
            }
        }
    }
}
