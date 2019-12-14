package code.aceptaelreto;

import java.util.HashMap;
import java.util.Scanner;

// https://www.aceptaelreto.com/problem/statement.php?id=214
public class AbdicacionRey {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numKing;
        HashMap<String, Integer> map = new HashMap<>();
        while (scanner.hasNextInt() && (numKing = scanner.nextInt()) != 0) {
            map.clear();

            for (int i = 0; i < numKing; i++) {
                String name = scanner.next();
                if (map.containsKey(name)) {
                    map.put(name, map.get(name) + 1);
                } else {
                    map.put(name, 1);
                }
            }

            for (int j = scanner.nextInt(); j > 0; j--) {
                String candidate = scanner.next();
                if (map.containsKey(candidate)) {
                    map.put(candidate, map.get(candidate) + 1);
                } else {
                    map.put(candidate, 1);
                }
                System.out.println(map.get(candidate));
            }
            System.out.println();
        }
    }
}
