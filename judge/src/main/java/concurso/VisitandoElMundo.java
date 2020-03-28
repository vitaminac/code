package concurso;

import java.util.HashSet;
import java.util.Scanner;

public class VisitandoElMundo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int T = scanner.nextInt();
        for (int i = 0; i < T; i++) {
            HashSet<Long> cities = new HashSet<>();
            int N = scanner.nextInt();
            for (int j = 0; j < N; j++) {
                cities.add(scanner.nextLong());
                cities.add(scanner.nextLong());
            }
            System.out.println(cities.size());
        }
    }
}
