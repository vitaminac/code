package code.curso;

import java.util.Scanner;

public class LaberintoAdvanzado {
    public static int find(int laberinto[][], int x, int y, int step, int visited, int best) {
        if (x < 0 || y < 0 || x >= laberinto.length || y >= laberinto[0].length) {
            return best;
        } else if (laberinto[x][y] == 2) {
            return best;
        } else if (laberinto[x][y] == 1 && ~visited == 0 /* comprueba que todos estan visitado */) {
            return Math.min(step, best);
        } else {
            int backup = laberinto[x][y];
            laberinto[x][y] = 2;
            best = find(laberinto, x + 1, y, step + 1, visited | 1 << backup, best);
            best = find(laberinto, x - 1, y, step + 1, visited | 1 << backup, best);
            best = find(laberinto, x, y + 1, step + 1, visited | 1 << backup, best);
            best = find(laberinto, x, y - 1, step + 1, visited | 1 << backup, best);
            laberinto[x][y] = backup;
            return best;
        }
    }

    public static void main(String args[]) {
        try (Scanner scanner = new Scanner(System.in)) {
            int N = scanner.nextInt();
            int M = scanner.nextInt();
            int S = scanner.nextInt();
            int laberinto[][] = new int[N][M];
            int initialX = 0;
            int initialY = 0;
            for (int i = 0; i < N * M; i++) {
                int number = scanner.nextInt();
                laberinto[i / M][i % M] = number;
                if (number == 0) {
                    initialX = i / M;
                    initialY = i % M;
                }
            }
            int visited = ~((int) Math.pow(2, S + 3) - 1);
            // ignorar 2 (separacion) y 1(salida)
            visited += 6;
            System.out.println(find(laberinto, initialX, initialY, 1, visited, N * M));
        }
    }
}