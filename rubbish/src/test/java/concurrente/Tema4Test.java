package concurrente;

import org.junit.Test;

import java.io.File;

public class Tema4Test {

    @Test
    public void message() throws InterruptedException {
        Tema4.message(1000);
    }

    @Test
    public void tickets() throws InterruptedException {
        Tema4.tickets(100, 100, 100);
    }

    @Test
    public void server() throws InterruptedException {
        Tema4.server(100, 100);
    }

    @Test
    public void findDuplicates() {
        Tema4.findDuplicates(new File("C:\\Temp"));
    }

    @Test
    public void pc() throws InterruptedException {
        Tema4.pc(100, 10, 100);
    }

    @Test
    public void countdown() throws InterruptedException {
        Tema4.countdown();
    }

    @Test
    public void cyclic_barrier() throws InterruptedException {
        Tema4.cyclic_barrier(10);
    }
}