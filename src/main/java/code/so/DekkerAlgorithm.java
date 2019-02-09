package code.so;

import java.util.Scanner;

/**
 * https://www.geeksforgeeks.org/operating-system-dekkers-algorithm/
 * <p>
 * To obtain such a mutual exclusion, bounded waiting,
 * and progress there have been several algorithms implemented,
 * one of which is Dekker’s Algorithm.
 * <p>
 * Dekker’s algorithm was the first provably-correct solution to the critical section problem.
 * It allows two threads to share a single-use resource without conflict,
 * using only shared memory for communication.
 * <p>
 * It avoids the strict alternation of a naïve turn-taking algorithm,
 * and was one of the first mutual exclusion algorithms to be invented.
 * <p>
 * Although there are many versions of Dekker’s Solution,
 * the final or 5th version is the one that satisfies all of the above conditions
 * and is the most efficient of them all.
 * <p>
 * Dekker’s Solution, mentioned here,
 * ensures mutual exclusion between two processes only,
 * it could be extended to more than two processes with the proper use of arrays and variables.
 * <p>
 * var flag: array [0..1] of boolean;
 * turn: 0..1;
 * repeat
 * <p>
 * flag[i] := true;
 * while flag[j] do
 * if turn = j then
 * begin
 * flag[i] := false;
 * while turn = j do no-op;
 * flag[i] := true;
 * end;
 * <p>
 * critical section
 * <p>
 * turn := j;
 * flag[i] := false;
 * <p>
 * remainder section
 * <p>
 * until false;
 */
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
