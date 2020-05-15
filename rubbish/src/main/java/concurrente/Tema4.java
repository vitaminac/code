package concurrente;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class Tema4 {
    private Tema4() {
    }

    public static void message(int timeout) throws InterruptedException {
        Thread message = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("Say something...");
                Thread.yield();
            }
        });
        message.start();
        for (int i = 0; i < 5 && message.isAlive(); i++) {
            System.out.println("Waiting");
            Thread.sleep(timeout);
        }
        message.interrupt();
        System.out.println("Finish");
        message.join();
    }

    private static class TicketOffice {
        private Deque<Integer> requests = new LinkedList<>();
        private double sold = 0;

        public synchronized void buy(final int id) {
            this.requests.add(id);
            System.out.println("Client " + id + " want to buy");
            this.notifyAll();
        }

        public synchronized void sell(final int id) {
            while (this.requests.isEmpty()) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Seller sold " + " a ticket to " + this.requests.pop());
            this.sold += 1;
        }

        public synchronized void show() {
            System.out.println("Sold total " + sold + " tickets");
        }
    }

    public static void tickets(final int n_clients, final int n_seller, final int n_operation) throws InterruptedException {
        final TicketOffice office = new TicketOffice();
        final Thread[] clients = new Thread[n_clients];
        final Thread[] sellers = new Thread[n_seller];
        for (int i = 0; i < n_clients; i++) {
            final int id = i;
            clients[i] = new Thread(() -> {
                for (int j = 0; j < n_operation; j++) {
                    office.buy(id);
                }
            });
        }
        for (int i = 0; i < n_seller; i++) {
            final int id = i;
            sellers[i] = new Thread(() -> {
                for (int j = 0; j < n_operation; j++) {
                    office.sell(id);
                }
            });
        }
        for (var client : clients) client.start();
        for (var seller : sellers) seller.start();
        for (var client : clients) client.join();
        for (var seller : sellers) seller.join();
    }

    public static class Server {
        private Lock lock = new ReentrantLock();
        private int nWorker = 0;
        private int nPendingRequest = 0;
        private Condition empty_request = lock.newCondition();
        private Condition empty_worker = lock.newCondition();

        public void resolveRequest(int id) throws InterruptedException {
            System.out.println("detect " + id + " request");
            lock.lock();
            try {
                while (this.nWorker > 0) {
                    empty_worker.await();
                }
                this.nPendingRequest += 1;
            } finally {
                lock.unlock();
            }
            System.out.println(id + " request is processing. Total pending requests " + nPendingRequest);
            Thread.sleep(ThreadLocalRandom.current().nextInt(10));
            System.out.println(id + " request finishes processing");
            lock.lock();
            try {
                this.nPendingRequest -= 1;
                if (this.nPendingRequest == 0) {
                    this.empty_request.signalAll();
                }
            } finally {
                lock.unlock();
            }
            System.out.println(id + " ends for maintenance. Total pending requests " + this.nPendingRequest);
        }

        public void doMaintenance(int id) throws InterruptedException {
            System.out.println(id + " request try to start for maintenance");
            lock.lock();
            try {
                while (this.nPendingRequest > 0) {
                    this.empty_request.await();
                }
                this.nWorker += 1;
            } finally {
                lock.unlock();
            }
            System.out.println(id + " worker is working for maintenance. Total workers " + this.nWorker);
            Thread.sleep(ThreadLocalRandom.current().nextInt(10));
            System.out.println(id + " worker finishes working for maintenance");
            lock.lock();
            try {
                this.nWorker -= 1;
                if (this.nWorker == 0) {
                    this.empty_worker.signalAll();
                }
            } finally {
                lock.unlock();
            }
            System.out.println(id + " ends for maintenance");
        }
    }

    public static void server(int N, final int operation) throws InterruptedException {
        final Server server = new Server();
        Thread[] requests = new Thread[N];
        Thread[] workers = new Thread[N];
        for (int i = 0; i < N; i++) {
            final int id = i;
            requests[i] = new Thread(() -> {
                for (int j = 0; j < operation; j++) {
                    try {
                        server.resolveRequest(id);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        for (int i = 0; i < N; i++) {
            final int id = i;
            workers[i] = new Thread(() -> {
                for (int j = 0; j < operation; j++) {
                    try {
                        server.doMaintenance(id);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        for (Thread request : requests) request.start();
        for (Thread worker : workers) worker.start();
        for (Thread request : requests) request.join();
        for (Thread worker : workers) worker.join();
    }

    private static void findDuplicatesSequential(File root, HashMap<String, File> paths) {
        if (root.isDirectory()) {
            Optional.ofNullable(root.listFiles()).ifPresent(files -> {
                for (File file : files) {
                    findDuplicatesSequential(file, paths);
                }
            });
        } else {
            paths.compute(root.getName(), (name, file) -> {
                if (file == null) {
                    return root;
                } else {
                    System.out.println(root.getAbsolutePath() + " duplicated with " + file.getAbsolutePath());
                    return file;
                }
            });
        }
    }

    private static void findDuplicatesSynchronized(File root, HashMap<String, File> paths) {
        if (root.isDirectory()) {
            Thread[] threads = Arrays.stream(Objects.requireNonNull(root.listFiles()))
                    .map(file -> new Thread(() -> findDuplicatesSynchronized(file, paths))).toArray(Thread[]::new);
            for (Thread thread : threads) thread.start();
            for (Thread thread : threads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else {
            synchronized (paths) {
                paths.compute(root.getName(), (name, file) -> {
                    if (file == null) {
                        return root;
                    } else {
                        System.out.println(root.getAbsolutePath() + " duplicated with " + file.getAbsolutePath());
                        return file;
                    }
                });
            }
        }
    }

    private static void findDuplicatesConcurrentMap(File root, ConcurrentMap<String, File> paths) {
        if (root.isDirectory()) {
            Thread[] threads = Arrays.stream(Objects.requireNonNull(root.listFiles()))
                    .map(file -> new Thread(() -> findDuplicatesConcurrentMap(file, paths))).toArray(Thread[]::new);
            for (Thread thread : threads) thread.start();
            for (Thread thread : threads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else {
            paths.compute(root.getName(), (name, file) -> {
                if (file == null) {
                    return root;
                } else {
                    System.out.println(root.getAbsolutePath() + " duplicated with " + file.getAbsolutePath());
                    return file;
                }
            });
        }
    }

    /**
     * Se desea implementar de forma concurrente un
     * programa que busca ficheros con el mismo
     * nombre dentro de una carpeta
     * <p>
     * La búsqueda se realiza recursivamente en una
     * carpetas dentro de otras
     *
     * @param root
     */
    public static void findDuplicates(File root) {
        long startTime = System.nanoTime();
        findDuplicatesSequential(root, new HashMap<>());
        long sequentialTime = System.nanoTime() - startTime;
        startTime = System.nanoTime();
        findDuplicatesSynchronized(root, new HashMap<>());
        long synchronizedTime = System.nanoTime() - startTime;
        startTime = System.nanoTime();
        findDuplicatesConcurrentMap(root, new ConcurrentHashMap<>());
        long concurrentTime = System.nanoTime() - startTime;
        System.out.println("Sequential : " + sequentialTime / 1000);
        System.out.println("Synchronized : " + synchronizedTime / 1000);
        System.out.println("Concurrent : " + concurrentTime / 1000);
    }

    /**
     * Implementa el problema del productor-consumidor utilizando
     * colas bloqueantes
     */
    public static void pc(int N, int capacity, int n_product) throws InterruptedException {
        BlockingQueue<String> bq = new ArrayBlockingQueue<>(capacity);
        Thread[] producers = new Thread[N];
        Thread[] consumers = new Thread[N];

        for (int i = 0; i < N; i++) {
            final int id = i;
            producers[i] = new Thread(() -> {
                for (int j = 0; j < n_product; j++) {
                    String product = "product " + id + "_" + j;
                    System.out.println("Producer " + id + " produces " + product);
                    try {
                        bq.put(product);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        for (int i = 0; i < N; i++) {
            final int id = i;
            consumers[i] = new Thread(() -> {
                for (int j = 0; j < n_product; j++) {
                    try {
                        System.out.println("Consumer " + id + " consumes " + bq.take());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        for (Thread producer : producers) producer.start();
        for (Thread consumer : consumers) consumer.start();
        for (Thread producer : producers) producer.join();
        for (Thread consumer : consumers) consumer.join();
    }

    public static void countdown() throws InterruptedException {
        CountDownLatch A = new CountDownLatch(1);
        CountDownLatch B = new CountDownLatch(2);
        CountDownLatch C = new CountDownLatch(2);
        CountDownLatch D = new CountDownLatch(3);
        CountDownLatch E = new CountDownLatch(3);
        CountDownLatch F = new CountDownLatch(1);
        CountDownLatch G = new CountDownLatch(2);
        CountDownLatch H = new CountDownLatch(2);
        Thread p1 = new Thread(() -> {
            try {
                A.countDown();
                A.await();
                System.out.println("A");
                D.countDown();
                B.countDown();
                B.await();
                System.out.println("B");
                E.countDown();
                H.countDown();
                C.countDown();
                C.await();
                System.out.println("C");
                System.out.println("p1 finishes");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread p2 = new Thread(() -> {
            try {
                D.countDown();
                D.await();
                System.out.println("D");
                B.countDown();
                G.countDown();
                E.countDown();
                E.await();
                System.out.println("E");
                C.countDown();
                System.out.println("p2 finishes");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread p3 = new Thread(() -> {
            try {
                F.countDown();
                F.await();
                System.out.println("F");
                D.countDown();
                G.countDown();
                G.await();
                System.out.println("G");
                H.countDown();
                H.await();
                System.out.println("H");
                E.countDown();
                System.out.println("p3 finishes");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        p1.start();
        p2.start();
        p3.start();
        p1.join();
        p2.join();
        p3.join();
    }

    public static void cyclic_barrier(int N) throws InterruptedException {
        Thread[] threads = new Thread[N];
        CyclicBarrier barrier = new CyclicBarrier(N);
        for (int i = 0; i < N; i++) {
            threads[i] = new Thread(() -> {
                System.out.println(1);
                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println(2);
            });
        }
        for (Thread thread : threads) thread.start();
        for (Thread thread : threads) thread.join();
    }
}
