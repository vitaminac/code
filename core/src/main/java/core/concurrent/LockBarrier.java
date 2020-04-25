package core.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class LockBarrier implements Barrier {
    private final int nThread;
    private final Lock lock;
    private final Condition condition;
    private int nWaited = 0;

    public LockBarrier(int nThread, final Lock lock) {
        this.nThread = nThread;
        this.lock = lock;
        this.condition = lock.newCondition();
    }

    @Override
    public void await() throws InterruptedException {
        this.lock.lock();
        try {
            this.nWaited += 1;
            this.condition.signalAll();
            while (this.nWaited < this.nThread) {
                this.condition.await();
            }
        } finally {
            this.lock.unlock();
        }
    }
}
