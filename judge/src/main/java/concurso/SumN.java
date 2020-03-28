package concurso;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * Se pide implementar un algoritmo basado en la técnica de backtracking para
 * resolver el siguiente problema
 * 
 * Dada una lista L de números enteros no negativo, y otro número entero no
 * negativo s, se desea hallar un conjunto de número de L tal que su suma sea
 * igual a s, y que además dicho conjunto sea el de menor cardinalidad de entre
 * todos los posibles que cumplan la restricción asociada a la suma. El conjunto
 * se especificará mediante un vector de booleanos de la misma longitud que L.
 */
public class SumN {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            var N = scanner.nextInt();
            var numbers = new int[N];
            for (var i = 0; i < N; i++) {
                numbers[i] = scanner.nextInt();
            }
            var S = scanner.nextInt();
            Queue<Integer> values = new LinkedList<>();
            Queue<int[]> visiteds = new LinkedList<>();
            var currentValue = 0;
            var visited = new int[N];
            do {
                for (var i = 0; i < N; i++) {
                    if (visited[i] == 0) {
                        var newValue = currentValue + numbers[i];
                        if (newValue <= S) {
                            var newVisited = Arrays.copyOf(visited, visited.length);
                            newVisited[i] = 1;
                            visiteds.add(newVisited);
                            values.add(newValue);
                        }
                    }
                }
                currentValue = values.remove();
                visited = visiteds.remove();
            } while (currentValue != S);
            for (var i : visited) {
                System.out.print(i + " ");
            }
        }
    }
}