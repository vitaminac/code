package core.concurrent;

public interface Barrier {
    void await() throws InterruptedException;
}
