package concurrente;

import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;
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
}
