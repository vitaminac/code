package aceptaelreto;

import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Scanner;

public class P544QueNoSeAtraganten {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            while (sc.hasNextInt()) {
                int n = sc.nextInt();
                long p = sc.nextLong();
                PriorityQueue<Long> pq = new PriorityQueue<>(n, Collections.reverseOrder());
                for (int i = 0; i < n; i++) {
                    long uva = sc.nextLong();
                    if (uva <= p) {
                        if (pq.isEmpty()) {
                            pq.add(uva);
                        } else if (uva + pq.peek() <= p) {
                            pq.add(uva);
                        } else if (uva < pq.peek()) {
                            do {
                                pq.remove();
                            } while (!pq.isEmpty() && uva < pq.peek() && pq.peek() + uva > p);
                            pq.add(uva);
                        }
                    }
                }
                System.out.println(pq.size());
            }
        }
    }
}
