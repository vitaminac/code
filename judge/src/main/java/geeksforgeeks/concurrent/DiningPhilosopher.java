package geeksforgeeks.concurrent;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class DiningPhilosopher {
    public static void semaphore(final int N, final int iteration) throws InterruptedException {
        Semaphore[] chopsticks = new Semaphore[N];
        for (int i = 0; i < N; i++) chopsticks[i] = new Semaphore(1);
        Thread[] philosophers = new Thread[N];
        for (int i = 0; i < N; i++) {
            final int position = i;
            philosophers[i] = new Thread(() -> {
                for (int itr = 0; itr < iteration; itr++) {
                    try {
                        // Think
                        System.out.println("Philosopher " + position + " is thinking");
                        Thread.sleep(ThreadLocalRandom.current().nextInt(100));
                        // pickup chopsticks
                        System.out.println("Philosopher " + position + " is hungry");
                        if (position % 2 == 0) {
                            chopsticks[position].acquire();
                            chopsticks[(position + 1) % N].acquire();
                        } else {
                            chopsticks[(position + 1) % N].acquire();
                            chopsticks[position].acquire();
                        }
                        // Eat
                        System.out.println("Philosopher " + position + " is eating");
                        Thread.sleep(ThreadLocalRandom.current().nextInt(100));
                        // put down chopsticks
                        chopsticks[position].release();
                        chopsticks[(position + 1) % N].release();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        for (Thread philosopher : philosophers) philosopher.start();
        for (Thread philosopher : philosophers) philosopher.join();
    }
}
