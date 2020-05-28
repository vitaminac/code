package concurrente.practise;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Vehiculo. Entidad que representa cada uno de los coches que
 * componen la parrilla de salida y que correran en la carrera.
 * <p>
 * Clase Vehicle (Vehiculo) encargada de modelar el coche que correra en
 * el circuito. Cada vehiculo deber? ser modelado teniendo en cuenta
 * caracteracticas tecnicas como: velocidad, gasto de combustible
 */
public class Vehicle {
    public final String name;

    // cada coche tendrá características técnicas diferentes
    // como, por ejemplo: mejor trazado de curvas, más aceleración en recta,
    // etc.
    private final int[] speedPerDificulty;
    private final int[] fuelCostPerGear;

    private final int fuelCapacity;

    private int fuel;

    private Lock blocking = new ReentrantLock();
    private Semaphore spaces = new Semaphore(2);

    public Vehicle(String name,
                   int[] speedPerDificulty,
                   int[] fuelCostPerGear,
                   int fuelCapacity) {
        this.name = name;
        this.speedPerDificulty = speedPerDificulty;
        this.fuelCostPerGear = fuelCostPerGear;
        this.fuelCapacity = fuelCapacity;
        this.fuel = fuelCapacity;
    }

    public int getFuel() {
        return fuel;
    }

    @Override
    public String toString() {
        return name;
    }

    public int drive(Racetrack racetrack) {
        // lock: si estoy conduciendo nadie me puede sobrepasar
        this.blocking.lock();
        //   System.out.println("DEBUG: " + this + " is driving");
        // quitar la combustible que he usado
        this.fuel -= this.fuelCostPerGear[racetrack.getDifficulty()];
        // en el marcha que permite la carrera a que distancia puedo correr en una simulacion
        return this.speedPerDificulty[racetrack.getDifficulty()];
    }

    // determinar si se puede terminar el tramo con el combustible actual
    public boolean canDrive(Racetrack racetrack) {
        return (((double) racetrack.getDistance()) / this.speedPerDificulty[racetrack.getDifficulty()]) * this.fuelCostPerGear[racetrack.getDifficulty()] <= this.fuel;
    }

    public void deposit() {
        this.fuel = this.fuelCapacity;
    }

    public void endDrive() {
        this.blocking.unlock();
        //   System.out.println("DEBUG " + this + " is done driving");
    }

    // Así mismo se pueden producir adelantamientos o posibles
    // problemas en un mismo tramo
    // puede ser adelantado
    public boolean tryPass() {
        if (this.blocking.tryLock()) {
            //     System.out.println("DEBUG " + this + " is going to be passed");
            try {
                return this.spaces.tryAcquire();
            } finally {
                this.blocking.unlock();
            }
        } else {
            return false;
        }
    }

    public void endPass() {
        this.spaces.release();
        // System.out.println("DEBUG " + this + " has been passed");
    }

    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    public static Vehicle createVehicle(String name) {
        return new Vehicle(
                name,
                new int[]{
                        RANDOM.nextInt(5, 15),
                        RANDOM.nextInt(15, 25),
                        RANDOM.nextInt(25, 40),
                        RANDOM.nextInt(40, 60),
                        RANDOM.nextInt(60, 120),
                        RANDOM.nextInt(120, 180),
                        RANDOM.nextInt(150, 220),
                },
                new int[]{
                        RANDOM.nextInt(13, 17),
                        RANDOM.nextInt(11, 15),
                        RANDOM.nextInt(9, 13),
                        RANDOM.nextInt(7, 11),
                        RANDOM.nextInt(5, 9),
                        RANDOM.nextInt(3, 7),
                        RANDOM.nextInt(1, 5),
                },
                RANDOM.nextInt(500, 700));
    }
}
