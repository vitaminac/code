package concurrente;

import org.junit.Test;

import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertEquals;

public class Semaphore_DBLockTest {
    private volatile int resource = 0;

    @Test
    public void test() throws InterruptedException {
        final int nReader = 100;
        final int nWriter = 10;
        final int nOperation = 100;
        Thread[] readers = new Thread[nReader];
        Thread[] writers = new Thread[nWriter];
        Semaphore_DB_Lock lock = new Semaphore_DB_Lock();
        for (int i = 0; i < nReader; i++) {
            final int id = i;
            readers[i] = new Thread(() -> {
                for (int j = 0; j < nOperation; j++) {
                    try {
                        lock.acquireDBReadLock(id);
                        System.out.println("Reader " + id + ": resource is " + this.resource);
                        lock.releaseDBReadLock(id);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        for (int i = 0; i < nWriter; i++) {
            final int id = i;
            writers[i] = new Thread(() -> {
                for (int j = 0; j < nOperation; j++) {
                    try {
                        lock.acquireDBWriteLock(id);
                        this.resource += 1;
                        lock.releaseDBWriteLock(id);
                        Thread.sleep(ThreadLocalRandom.current().nextInt(1));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        for (int i = 0; i < nReader; i++) readers[i].start();
        for (int i = 0; i < nWriter; i++) writers[i].start();
        for (int i = 0; i < nReader; i++) readers[i].join();
        for (int i = 0; i < nWriter; i++) writers[i].join();
        assertEquals(nWriter * nOperation, this.resource);
    }
}