package code.geekforgeek.concurrent;

import java.util.Scanner;

public class BakeryAlgorithmTest {
    private static class Agent implements Runnable {
        private final int id;
        private final int iteration;
        private final BakeryAlgorithm<Integer> mutex;

        public Agent(int id, int iteration, BakeryAlgorithm<Integer> mutex) {
            this.id = id;
            this.iteration = iteration;
            this.mutex = mutex;
        }

        @Override
        public void run() {
            for (int i = 0; i < this.iteration; i++) {
                mutex.lock(this.id);
                this.mutex.getResource().set(this.mutex.getResource().get() + 1);
                mutex.unlock(this.id);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        int NUM_THREAD = 10;
        int iteration = new Scanner(System.in).nextInt();

        final BakeryAlgorithm<Integer> mutex = new BakeryAlgorithm<>(new BakeryAlgorithm.Resource<Integer>() {
            private volatile int resource = 0;

            @Override
            public Integer get() {
                return this.resource;
            }

            @Override
            public void set(Integer value) {
                this.resource = value;
            }
        }, NUM_THREAD, new BakeryAlgorithm.Choosing() {
            private volatile boolean c0;
            private volatile boolean c1;
            private volatile boolean c2;
            private volatile boolean c3;
            private volatile boolean c4;
            private volatile boolean c5;
            private volatile boolean c6;
            private volatile boolean c7;
            private volatile boolean c8;
            private volatile boolean c9;

            @Override
            public boolean get(int index) {
                switch (index) {
                    case 0:
                        return c0;
                    case 1:
                        return c1;
                    case 2:
                        return c2;
                    case 3:
                        return c3;
                    case 4:
                        return c4;
                    case 5:
                        return c5;
                    case 6:
                        return c6;
                    case 7:
                        return c7;
                    case 8:
                        return c8;
                    case 9:
                        return c9;
                    default:
                        throw new RuntimeException();
                }
            }

            @Override
            public void set(int index, boolean value) {
                switch (index) {
                    case 0:
                        c0 = value;
                        break;
                    case 1:
                        c1 = value;
                        break;
                    case 2:
                        c2 = value;
                        break;
                    case 3:
                        c3 = value;
                        break;
                    case 4:
                        c4 = value;
                        break;
                    case 5:
                        c5 = value;
                        break;
                    case 6:
                        c6 = value;
                        break;
                    case 7:
                        c7 = value;
                        break;
                    case 8:
                        c8 = value;
                        break;
                    case 9:
                        c9 = value;
                        break;
                    default:
                        throw new RuntimeException();
                }
            }
        }, new BakeryAlgorithm.Tickets() {
            private volatile int c0;
            private volatile int c1;
            private volatile int c2;
            private volatile int c3;
            private volatile int c4;
            private volatile int c5;
            private volatile int c6;
            private volatile int c7;
            private volatile int c8;
            private volatile int c9;

            @Override
            public int get(int index) {
                switch (index) {
                    case 0:
                        return c0;
                    case 1:
                        return c1;
                    case 2:
                        return c2;
                    case 3:
                        return c3;
                    case 4:
                        return c4;
                    case 5:
                        return c5;
                    case 6:
                        return c6;
                    case 7:
                        return c7;
                    case 8:
                        return c8;
                    case 9:
                        return c9;
                    default:
                        throw new RuntimeException();
                }
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0:
                        c0 = value;
                        break;
                    case 1:
                        c1 = value;
                        break;
                    case 2:
                        c2 = value;
                        break;
                    case 3:
                        c3 = value;
                        break;
                    case 4:
                        c4 = value;
                        break;
                    case 5:
                        c5 = value;
                        break;
                    case 6:
                        c6 = value;
                        break;
                    case 7:
                        c7 = value;
                        break;
                    case 8:
                        c8 = value;
                        break;
                    case 9:
                        c9 = value;
                        break;
                    default:
                        throw new RuntimeException();
                }
            }
        });

        Thread[] threads = new Thread[NUM_THREAD];

        for (int i = 0; i < NUM_THREAD; i++) {
            threads[i] = new Thread(new Agent(i, iteration, mutex));
            threads[i].start();
        }

        for (int i = 0; i < NUM_THREAD; i++) {
            threads[i].join();
        }

        System.out.println(mutex.getResource().get());
    }
}