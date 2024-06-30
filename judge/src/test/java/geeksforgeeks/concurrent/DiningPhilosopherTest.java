package geeksforgeeks.concurrent;

import org.junit.Test;
import core.io.NullOutputStream;

import java.io.PrintStream;

public class DiningPhilosopherTest {

    @Test
    public void semaphore() throws InterruptedException {
        PrintStream original = System.out;
        System.setOut(new PrintStream(new NullOutputStream()));
        DiningPhilosopher.semaphore(10, 100);
        System.setOut(original);
    }
}