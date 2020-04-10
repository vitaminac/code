package geeksforgeeks.concurrent;

import java.util.Scanner;

// https://www.geeksforgeeks.org/operating-system-dekkers-algorithm/
public class DekkerAlgorithm implements Runnable {
    private static Turn turn;

    private static volatile int resource;
    private static int N;

    private static void lock(int thread) {
        // I want to enter
        turn.setIntent(thread, true);
        // If you want to enter
        while (turn.getIntent(1 - thread)) {
            // and if it's your turn
            if (turn.getTurn() == 1 - thread) {
                // I don't want to enter any more
                turn.setIntent(thread, false);
                // If it's your turn
                while (turn.getTurn() == 1 - thread) {
                    // I'll wait
                    Thread.yield();
                }
                // I want to enter
                turn.setIntent(thread, true);
            }
        }
    }

    private static void unlock(int thread) {
        // You can enter next
        turn.setTurn(1 - thread);
        // I don't want to enter any more
        turn.setIntent(thread, false);
    }

    public static void main(String[] args) throws Exception {
        N = new Scanner(System.in).nextInt();
        turn = new Turn();
        resource = 0;
        Thread left = new Thread(new DekkerAlgorithm(Turn.LEFT));
        Thread right = new Thread(new DekkerAlgorithm(Turn.RIGHT));

        left.start();
        right.start();

        left.join();
        right.join();

        System.out.println(resource);
    }

    private final int id;

    public DekkerAlgorithm(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        for (int i = 0; i < N; i++) {
            lock(this.id);
            resource++;
            unlock(this.id);
        }
    }
}
