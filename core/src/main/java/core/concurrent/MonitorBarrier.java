package core.concurrent;

public class MonitorBarrier implements Barrier {
    private final int nThread;
    private int nWaited = 0;

    public MonitorBarrier(int nThread) {
        this.nThread = nThread;
    }

    @Override
    public synchronized void await() throws InterruptedException {
        this.nWaited += 1;
        this.notifyAll();
        while (this.nWaited < this.nThread) {
            this.wait();
        }
    }
}
