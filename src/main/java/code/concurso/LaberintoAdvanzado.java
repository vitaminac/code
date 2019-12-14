package code.concurso;

import java.util.Scanner;

/**
 * Entrada La primera línea contiene tres enteros N, M y S que indican el número
 * de filas de espacios, el número de espacios por cada fila, y el número de
 * secciones en total que hay en ese Ikea. Las siguientes N líneas contienen N
 * enteros que indican lo que hay en cada uno de los M espacios de esa línea. La
 * entrada se denota por 0, la salida por 1, y las separaciones (que no son
 * transitables) por 2. Las secciones se denotan con los números de 3 en
 * adelante (si hay tres secciones, tendrán asignados los números 3, 4 y 5).
 * 
 * Salida La salida debe ser un número entero que contiene el número mínimo de
 * espacios que hay que recorrer desde la entrada a la salida para visitar todas
 * las secciones.
 */
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