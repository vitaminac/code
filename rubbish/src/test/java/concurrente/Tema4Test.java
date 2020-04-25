package concurrente;

import org.junit.Test;

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
}