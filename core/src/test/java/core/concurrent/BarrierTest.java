package core.concurrent;

import core.Utils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class BarrierTest {
    private static final int N = 100;
    private Supplier<Barrier> supplier;
    private Barrier barrier;

    public BarrierTest(Supplier<Barrier> supplier) {
        this.supplier = supplier;
    }

    @Before
    public void setUp() {
        this.barrier = this.supplier.get();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> initialize() {
        return Arrays.asList(new Object[][]{
                {(Supplier<SemaphoreBarrier>) () -> new SemaphoreBarrier(N)},
                {(Supplier<MonitorBarrier>) () -> new MonitorBarrier(N)},
                {(Supplier<LockBarrier>) () -> new LockBarrier(N, new ReentrantLock())},
        });
    }

    @Test
    public void await() throws InterruptedException {
        final StringBuffer sb = new StringBuffer();
        final Thread[] threads = new Thread[N];
        for (int i = 0; i < N; i++) {
            threads[i] = new Thread(() -> {
                try {
                    sb.append(1);
                    this.barrier.await();
                    sb.append(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        for (Thread thread : threads) thread.start();
        for (Thread thread : threads) thread.join();
        assertEquals(Utils.repeat("1", N) + Utils.repeat("2", N), sb.toString());
    }
}