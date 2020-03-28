package aceptaelreto;

import java.util.*;

public class P288PetrolerosHundidos {
    private static int[] size = new int[1000 * 1000];
    private static int R;
    private static int C;
    private static char[][] sea = new char[1000][];
    private static int[] uf = new int[1000 * 1000];
    private static int max;

    private static int find(int i) {
        if (uf[i] == i) return i;
        return uf[i] = find(uf[i]);
    }

    private static void search(int i, int j, int parent) {
        if (i < 0 || j < 0 || i == R || j == C) return;
        switch (sea[i][j]) {
            case '#':
                sea[i][j] = '*';
                uf[i * C + j] = parent;
                size[parent] += 1;
                max = Math.max(max, size[parent]);
                search(i - 1, j - 1, parent);
                search(i - 1, j, parent);
                search(i - 1, j + 1, parent);
                search(i, j - 1, parent);
                search(i, j + 1, parent);
                search(i + 1, j - 1, parent);
                search(i + 1, j, parent);
                search(i + 1, j + 1, parent);
                break;
            case '*':
                int root = find(i * C + j);
                if (root != parent) {
                    uf[root] = parent;
                    size[parent] += size[root];
                    max = Math.max(max, size[parent]);
                }
                break;
            default:
        }
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            while (sc.hasNextInt()) {
                R = sc.nextInt();
                C = sc.nextInt();
                max = 0;
                for (int i = R * C - 1; i >= 0; i--) size[i] = 0;

                sc.nextLine();
                for (int i = 0; i < R; i++) {
                    sea[i] = sc.nextLine().toCharArray();
                }

                for (int i = 0; i < R; i++) {
                    for (int j = 0; j < C; j++) {
                        if (sea[i][j] == '#') search(i, j, i * C + j);
                    }
                }

                int N = sc.nextInt();
                StringBuilder sb = new StringBuilder();
                sb.append(max);
                for (int n = 0; n < N; n++) {
                    sb.append(' ');
                    int i = sc.nextInt() - 1;
                    int j = sc.nextInt() - 1;
                    if (sea[i][j] == ' ') {
                        sea[i][j] = '#';
                        search(i, j, i * C + j);
                    }
                    sb.append(max);
                }
                System.out.println(sb.toString());
            }
        }
    }
}
