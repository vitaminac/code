package aceptaelreto;

import java.util.*;

public class P546IntercambiandoCromos {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int T = sc.nextInt();
            Map<Integer, Integer> alejandro = new TreeMap<>();
            Map<Integer, Integer> mateo = new TreeMap<>();
            StringBuilder sb = new StringBuilder();
            for (int t = 0; t < T; t++) {
                int N = sc.nextInt();
                alejandro.clear();
                for (int i = 0; i < N; i++) {
                    int cromo = sc.nextInt();
                    alejandro.put(cromo, alejandro.containsKey(cromo) ? alejandro.get(cromo) + 1 : 1);
                }
                int M = sc.nextInt();
                mateo.clear();
                for (int i = 0; i < M; i++) {
                    int cromo = sc.nextInt();
                    mateo.put(cromo, mateo.containsKey(cromo) ? mateo.get(cromo) + 1 : 1);
                }
                sb.setLength(0);
                for (Map.Entry<Integer, Integer> entry : alejandro.entrySet()) {
                    if (entry.getValue() > 1 && !mateo.containsKey(entry.getKey())) {
                        sb.append(entry.getKey());
                        sb.append(' ');
                    }
                }
                if (sb.length() == 0) {
                    System.out.println("Nada que intercambiar");
                } else {
                    sb.setLength(sb.length() - 1);
                    System.out.println(sb.toString());
                }
                sb.setLength(0);
                for (Map.Entry<Integer, Integer> entry : mateo.entrySet()) {
                    if (entry.getValue() > 1 && !alejandro.containsKey(entry.getKey())) {
                        sb.append(entry.getKey());
                        sb.append(' ');
                    }
                }
                if (sb.length() == 0) {
                    System.out.println("Nada que intercambiar");
                } else {
                    sb.setLength(sb.length() - 1);
                    System.out.println(sb.toString());
                }
            }
        }
    }
}
