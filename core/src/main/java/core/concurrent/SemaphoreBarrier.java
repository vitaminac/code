package core.concurrent;

import java.util.concurrent.Semaphore;

public class SemaphoreBarrier implements Barrier {
    private final int nThread;
    private volatile int nWaited = 0;
    private Semaphore semaphore = new Semaphore(0);
    private Semaphore lock = new Semaphore(1);

    public SemaphoreBarrier(final int nThread) {
        this.nThread = nThread;
    }

    @Override
    public void await() throws InterruptedException {
        lock.acquire();
        this.nWaited += 1;
        if (this.nWaited == this.nThread) {
            for (int i = 0; i < this.nThread; i++) {
                this.semaphore.release();
            }
        }
        this.lock.release();
        this.semaphore.acquire();
    }
}
