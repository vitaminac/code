package geeksforgeeks.concurrent;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class DiningPhilosopher {
    private static void eat(int philosopherId) throws InterruptedException {
        System.out.println("Philosopher " + philosopherId + " is eating");
        Thread.sleep(ThreadLocalRandom.current().nextInt(100));
    }

    private static void think(int philosopherId) throws InterruptedException {
        System.out.println("Philosopher " + philosopherId + " is thinking");
        Thread.sleep(ThreadLocalRandom.current().nextInt(100));
        System.out.println("Philosopher " + philosopherId + " is hungry");
    }

    public static void deadlock(final int N, final int iteration) throws Exception {
        final Semaphore[] chopsticks = new Semaphore[N];
        for (int i = 0; i < N; i++) chopsticks[i] = new Semaphore(1);
        final Thread[] philosophers = new Thread[N];
        for (int i = 0; i < N; i++) {
            final int philosopherId = i;
            philosophers[i] = new Thread(() -> {
                for (int itr = 0; itr < iteration; itr++) {
                    try {
                        think(philosopherId);
                        // pickup chopsticks
                        chopsticks[philosopherId].acquire();
                        chopsticks[(philosopherId + 1) % N].acquire();

                        eat(philosopherId);
                        // put down chopsticks
                        chopsticks[philosopherId].release();
                        chopsticks[(philosopherId + 1) % N].release();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        for (final var philosopher : philosophers) philosopher.start();
        for (final var philosopher : philosophers) philosopher.join();
    }

    private static boolean tryPickup(final Semaphore[] chopsticks, final int N, final int philosopherId) {
        if (chopsticks[philosopherId].tryAcquire()) {
            if (chopsticks[(philosopherId + 1) % N].tryAcquire()) {
                return true;
            } else {
                chopsticks[philosopherId].release();
            }
        }
        return false;
    }

    public static void livelock(final int N, final int iteration) throws Exception {
        final Semaphore[] chopsticks = new Semaphore[N];
        for (int i = 0; i < N; i++) chopsticks[i] = new Semaphore(1);
        final Thread[] philosophers = new Thread[N];
        for (int i = 0; i < N; i++) {
            final int philosopherId = i;
            philosophers[i] = new Thread(() -> {
                for (int itr = 0; itr < iteration; itr++) {
                    try {
                        think(philosopherId);
                        // pickup chopsticks
                        while (!tryPickup(chopsticks, N, philosopherId)) ;
                        eat(philosopherId);
                        // put down chopsticks
                        chopsticks[philosopherId].release();
                        chopsticks[(philosopherId + 1) % N].release();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        for (final var philosopher : philosophers) philosopher.start();
        for (final var philosopher : philosophers) philosopher.join();
    }

    public static void semaphore(final int N, final int iteration) throws InterruptedException {
        final Semaphore[] chopsticks = new Semaphore[N];
        for (int i = 0; i < N; i++) chopsticks[i] = new Semaphore(1);
        final Thread[] philosophers = new Thread[N];
        for (int i = 0; i < N; i++) {
            final int philosopherId = i;
            philosophers[i] = new Thread(() -> {
                for (int itr = 0; itr < iteration; itr++) {
                    try {
                        think(philosopherId);
                        // pickup chopsticks
                        if (philosopherId % 2 == 0) {
                            chopsticks[philosopherId].acquire();
                            chopsticks[(philosopherId + 1) % N].acquire();
                        } else {
                            chopsticks[(philosopherId + 1) % N].acquire();
                            chopsticks[philosopherId].acquire();
                        }
                        eat(philosopherId);
                        // put down chopsticks
                        chopsticks[philosopherId].release();
                        chopsticks[(philosopherId + 1) % N].release();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        for (final var philosopher : philosophers) philosopher.start();
        for (final var philosopher : philosophers) philosopher.join();
    }
}
