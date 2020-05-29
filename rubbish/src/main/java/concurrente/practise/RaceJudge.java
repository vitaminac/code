package concurrente.practise;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class RaceJudge {
    private CyclicBarrier barrier;
    private CountDownLatch countdown;

    public RaceJudge(final int n_vehicle) {
        // one count more to ensure only we call start will actually start
        this.barrier = new CyclicBarrier(n_vehicle + 1);
        this.countdown = new CountDownLatch(n_vehicle + 1);
    }

    public void prepare() throws InterruptedException, BrokenBarrierException {
        this.barrier.await();
    }

    /**
     * El juez de carrera sera el encargado de dar el pistoletazo de salida,
     *
     * @throws InterruptedException
     */
    public void start() throws InterruptedException, BrokenBarrierException {
        this.barrier.await();
        System.out.println("Juez ha dado pistoletazo de salida");
    }

    public void enterBox() {
        try {
            synchronized (this.countdown) {
                this.countdown.countDown();
            }
            this.countdown.await();
        } catch (InterruptedException e) {
            // suspension de la carrera
            // e.printStackTrace();
        }
    }

    // controlar la estancia de los vehículos en boxes y revisar que todo el
    // mundo ha acabado la carrera correctamente.
    public void awaitFinish() throws InterruptedException {
        synchronized (this.countdown) {
            this.countdown.countDown();
        }
        this.countdown.await();
        System.out.println("Juez ha dado la finalizacion del circuito");
    }

    public void suspend() {
        synchronized (this.countdown) {
            while (this.countdown.getCount() > 0) {
                this.countdown.countDown();
            }
        }
    }
}
