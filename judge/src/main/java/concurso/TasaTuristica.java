package concurso;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class TasaTuristica {
    public static void main(String[] args) {
        Set<Integer> odd = new HashSet<>();
        try (Scanner sc = new Scanner(System.in)) {
            while (sc.hasNextInt()) {
                int N = sc.nextInt();
                odd.clear();
                for (int i = 0; i < N; i++) {
                    int C = sc.nextInt();
                    if (odd.contains(C)) odd.remove(C);
                    else odd.add(C);
                }
                System.out.println(odd.isEmpty() ? "Todos pagaron" : "Alguien falta");
            }
        }
    }
}
