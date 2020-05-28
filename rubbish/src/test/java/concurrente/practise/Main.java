package concurrente.practise;

import java.io.IOException;
import java.util.concurrent.BrokenBarrierException;

/**
 * El objetivo de la practica es
 * implementar un programa concurrente en java
 * que simule carreras de coches de Formula 1.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
        int N_TRACKS = 10;
        int N_PARTICIPANTS = 50;
        int N_LAPS = 58;

        final Race race = new Race(N_TRACKS, N_PARTICIPANTS, N_LAPS);

        // Despues, se crean N vehiculos cada uno con sus
        // especificaciones tecnicas.
        for (int i = 0; i < N_PARTICIPANTS; i++) {
            race.addParticipant(Vehicle.createVehicle(String.format("coche#%02d", i)));
        }

        // empezar la carrera
        race.start();

        // si se introduce algún valor se
        // deberá parar la carrera
        // y finalizar mostrando el resultado una vez se paren
        // todos los vehículos.
        Thread waitInput = new Thread(() -> {
            try {
                while (System.in.available() == 0 && !Thread.currentThread().isInterrupted()) {
                    Thread.yield();
                }
                if (!Thread.currentThread().isInterrupted()) {
                    race.suspend();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        waitInput.start();

        // cerrar la carrera y liberar los recursos
        race.awaitFinish();
        waitInput.interrupt();

        waitInput.join();
    }
}
