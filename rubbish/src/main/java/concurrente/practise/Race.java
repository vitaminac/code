package concurrente.practise;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.StampedLock;

/**
 * Entidad que representa en lugar donde corren los vehiculos.
 * <p>
 * Clase Race (Carrera) encargada de modelar el circuito donde correrán los
 * coches. Una carrera se encuentra compuesta por una serie de tramos que
 * pueden tener distintas formas, como se ha indicado anteriormente. El
 * objetivo de esta clase será la de gestionar la lógica del movimiento de los
 * vehículos, informar al panel de control con datos
 * "actuales" de los vehículos como posición actual, metros hacia la meta,
 * distancia entre vehículos, etc
 */
public class Race {
    // los vehiculos seran invocados cada 5 segundos
    private static final int DELAY = 1;

    private final List<Integer> total_distances = new ArrayList<>();
    // Un circuito se compone de una serie de tramos
    private final List<Racetrack> racetracks = new ArrayList<>();
    private final Score score;
    private final RaceJudge raceJudge;
    private final ScheduledThreadPoolExecutor executorService;

    // cuantas vueltas
    private final int laps;

    private int total_distance = 0;

    // posiciones de cada vehiculo, cada piloto solo modifica su position
    // Informar contrincantes de la posición de los otros vehículos.
    private final ConcurrentMap<Vehicle, Integer> distances = new ConcurrentHashMap<>();

    // ReadWriteLock no permite convertir un read lock a write lock
    private final StampedLock rankingControl = new StampedLock();
    private final ConcurrentMap<Vehicle, Integer> vehicleToRanking = new ConcurrentHashMap<>();
    private final ConcurrentMap<Integer, Vehicle> rankingToVehicle = new ConcurrentHashMap<>();

    public Race(
            int n_tracks,
            int n_participants,
            int laps) {
        this.score = new Score();
        this.executorService = new ScheduledThreadPoolExecutor(n_participants + 1);
        this.laps = laps;
        this.executorService.setRemoveOnCancelPolicy(true);
        this.raceJudge = new RaceJudge(n_participants);
        // se crea un circuito a partir de
        //diversos tramos
        for (int i = 0; i < n_tracks; i++) {
            this.addRacetrack(Racetrack.randomCreate("tramo#" + i,
                    ThreadLocalRandom.current().nextInt(20, 500)));
        }
    }

    // En función de cómo se
    //organicen estos tramos darán lugar a distintos tipos de circuitos y,
    // por lo tanto, distintos tipos de carreras.
    public void addRacetrack(Racetrack racetrack) {
        this.racetracks.add(racetrack);
        total_distance += racetrack.getDistance();
        this.total_distances.add(total_distance);
    }

    public void addParticipant(final Vehicle vehicle) {
        this.score.enterBoxForStart(vehicle);
        // iniciar el ranking, al empezar
        // todos tienes un ranking por defecto que es orden de entrada
        this.vehicleToRanking.put(vehicle, this.vehicleToRanking.size() + 1);
        this.rankingToVehicle.put(this.rankingToVehicle.size() + 1, vehicle);
        // poner valor de distance
        this.distances.put(vehicle, 0);
        // los vehiculos sera invocados cada 5 segundos,
        this.executorService.execute(() -> {
            try {
                // esperar a pistoletazo de racejudge
                Race.this.raceJudge.prepare();
                Race.this.createPilot(vehicle).run();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        });
    }

    public void start() throws InterruptedException, BrokenBarrierException {
        // el juez de carrera comprueba que
        // los vehiculos están bien en la parrilla de salida, y
        // si está bien da el pistoletazo
        // de salida.
        this.raceJudge.start();
        // set el tiempo de start
        score.notifyStart();
        // activar el panel de informacion para actualizacion
        // actualizar el panel constantemente hasta que termine el programa
        this.executorService.scheduleAtFixedRate(this.score::showPanel, 0, 5000, TimeUnit.MILLISECONDS);
    }

    // La carrera terminará cuando todos los vehículos pasen la línea de meta,
    // den una vuelta al circuito extra y acaben en boxes.
    public void awaitFinish() throws InterruptedException {
        this.raceJudge.awaitFinish();
        // terminar worker para panel de informacion para actualizacion
        this.executorService.shutdown();
        // actualiza la ultima mensaje de panel de informacion
        this.score.notifyFinish();
    }

    public void suspend() {
        this.executorService.shutdown();
        try {
            this.executorService.awaitTermination(DELAY, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.score.notifySuspend();
        this.raceJudge.suspend();
        this.score.showPanel();
    }

    public Racetrack getCurrentRaceTrack(Vehicle vehicle) {
        // solo el hido vehilo midifica su posicion
        // y un vehiculo solo tiene un hilo ejecutando por la propiedad de matodod de ejecutor
        int offset = this.distances.get(vehicle) % this.total_distance;
        int index = Collections.binarySearch(this.total_distances, offset);
        if (index < 0) index = -(index + 1);
        return this.racetracks.get(index);
    }

    public int getCurrentLap(Vehicle vehicle) {
        return this.distances.get(vehicle) / Race.this.total_distance + 1;
    }

    public int getPositionOnCurrentLap(final Vehicle vehicle) {
        return (this.distances.get(vehicle) % this.total_distance) * 100 / this.total_distance;
    }

    /**
     * cuánto avanza dentro del tramo y, en caso de
     * adelantamiento, si debieran avanzar uno más que su contrincante.
     */
    public int updateRanking(final Vehicle vehicle, final int newDistance) {
        // cuando leo ranking de vehiculo nadie puede modificar el ranking
        long readStamp = this.rankingControl.readLock();
        if (!this.rankingControl.validate(readStamp)) throw new RuntimeException("fatal aqui 1");
        try {
            int rank;
            Vehicle prev = null;
            // si no estamos en el primero
            // y que el contrincantes no nos bloquea
            while (this.rankingControl.validate(readStamp) && (rank = this.vehicleToRanking.get(vehicle)) > 1 && (prev = this.rankingToVehicle.get(rank - 1)).tryPass()) {
                try {
                    // acceder disances sin lock a distancia es valido,
                    // por que otro vehiculo no puede mover si cogemos su lock
                    // si hay otro coche que le está adelantando entonces tampoco se puede adelantar
                    // si un vehiculo 
                    if (this.distances.get(prev) < newDistance) {
                        // ahora queremos modificar ranking tenemos que tener write lock
                        long writeStamp = this.rankingControl.tryConvertToWriteLock(readStamp);
                        // si obtenemos el write acceso
                        if (this.rankingControl.validate(writeStamp)) {
                            try {
                                this.rankingToVehicle.put(rank - 1, vehicle);
                                this.vehicleToRanking.put(vehicle, rank - 1);
                                this.rankingToVehicle.put(rank, prev);
                                this.vehicleToRanking.put(prev, rank);
                            } finally {
                                // terminamos de modificar el ranking y back to readlock
                                readStamp = this.rankingControl.tryConvertToReadLock(writeStamp);
                            }
                            if (!this.rankingControl.validate(readStamp))
                                throw new RuntimeException("fatal aqui 3");
                            this.score.changeRanking(vehicle, prev);
                        }
                    } else {
                        this.score.forPass(vehicle, this.distances.get(prev) - newDistance);
                        return newDistance;
                    }
                } finally {
                    prev.endPass();
                }
            }

            // ya no podemos pasar a mas coches, o que nos bloquea o por otro problem
            // la posicion de este coche no puede ser mayor que el anterior coche
            if (prev == null) {
                this.score.forPass(vehicle, -1);
                return newDistance;
            } else return Math.min(newDistance, this.distances.get(prev));

        } finally {
            if (!this.rankingControl.validate(readStamp)) throw new RuntimeException("fatal aqui 4");
            // termino de modificar con ranking ya no necesitamos, dejamos el acceso de raed lock
            Race.this.rankingControl.unlockRead(readStamp);
        }
    }

    public Runnable createPilot(final Vehicle vehicle) {
        return () -> {
            this.score.vehicleStarted(vehicle);
            new Runnable() {
                @Override
                public void run() {
                    // cuantas vuelta ha dado
                    int prevLap = Race.this.getCurrentLap(vehicle);
                    // cuando estoy avanzado nadie puede avanzar a mi
                    // solo cuando no he completado la carrera puede avanzarme
                    Racetrack raceTrack = Race.this.getCurrentRaceTrack(vehicle);
                    int advance = vehicle.drive(raceTrack);
                    try {
                        // si debieran avanzar
                        // uno más que su contrincante.
                        advance = Race.this.updateRanking(vehicle, Race.this.distances.get(vehicle) + advance);
                        Race.this.distances.put(vehicle, advance);
                        // El circuito informara al panel de control
                        // con datos "actuales" de los vehiculos como posicion
                        // actual, metros hacia la meta, distancia entre vehiulos, etc.
                        // Como es logico, el avance de cada tramo har?
                        // que los vehiculos consuman combustible,
                        // teniendo que pasar a boxes a repostar cuando se encuentren
                        // en la reserva.
                        // Cada vez que un vehículo avanza al siguiente tramo se actualizaran
                        // aspectos relacionados con velocidad, combustible, etc.
                        Race.this.score.updatePosition(vehicle, total_distance * laps - Race.this.distances.get(vehicle), Race.this.getPositionOnCurrentLap(vehicle));
                        Runnable nextAction = Race.this.checkCompleted(vehicle) ? Race.this.pilotLastLap(vehicle) : this;
                        Race.this.checkNextLap(vehicle, prevLap);
                        // Los vehículos tendrán que decidir si avanzan
                        // o no al siguiente tramo
                        // si tiene suficiente combustible
                        if (Race.this.checkFuel(vehicle, raceTrack, nextAction)) {
                            // esperar cinco minuto
                            Race.this.executorService.schedule(nextAction, DELAY, TimeUnit.MILLISECONDS);
                        }
                    } finally {
                        // ya hemos terminado el adelatamiento, no hace falta el blocking
                        vehicle.endDrive();
                    }
                }
            }.run();
        };
    }

    public Runnable pilotLastLap(final Vehicle vehicle) {
        // ya no modificamos el ranking
        return new Runnable() {
            @Override
            public void run() {
                try {
                    try {
                        Racetrack raceTrack = Race.this.getCurrentRaceTrack(vehicle);
                        int advance = vehicle.drive(raceTrack);
                        // siempre es presente solo para evitar warning de IDE
                        Race.this.distances.computeIfPresent(vehicle, (key, distance) -> distance + advance);
                        Race.this.score.updatePosition(vehicle, 0, Race.this.getPositionOnCurrentLap(vehicle));
                    } finally {
                        vehicle.endDrive();
                    }

                    // den una vuelta extra
                    if (Race.this.checkFinish(vehicle)) {
                        // terminen en boxed
                        Race.this.score.enterBoxFinish(vehicle);
                        Race.this.raceJudge.enterBox();
                    } else {
                        Race.this.executorService.schedule(this, DELAY, TimeUnit.MILLISECONDS);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public void checkNextLap(final Vehicle vehicle, final int prevLap) {
        // informar al panel de control
        // si empiezo la nueva vuelta chequeamos el combustible
        if (Race.this.getCurrentLap(vehicle) > prevLap) {
            this.score.enterNewLap(vehicle, Race.this.getCurrentLap(vehicle));
        }
    }

    public boolean checkFuel(final Vehicle vehicle, final Racetrack prevTrack, Runnable callback) {
        this.score.updateFuel(vehicle, vehicle.getFuel());
        if (this.getCurrentRaceTrack(vehicle) != prevTrack) {
            // Además, cada vehículo tendrá que decidir si avanzan o no
            // al siguiente tramo,
            if (!vehicle.canDrive(Race.this.getCurrentRaceTrack(vehicle))) {
                // hay que pasar por boxed
                this.score.enterBoxForFuel(vehicle);
                vehicle.deposit();
                // En el caso en el
                // que no quede combustible el Vehículo tendrá que ir a boxes durante 2
                // simulaciones.
                this.executorService.schedule(() -> {
                    this.score.exitBoxForRunning(vehicle);
                    callback.run();
                }, 2 * DELAY, TimeUnit.MILLISECONDS);
                return false;
            }
        }
        return true;
    }

    public boolean checkCompleted(Vehicle vehicle) {
        if (Race.this.getCurrentLap(vehicle) > Race.this.laps) {
            this.score.vehicleStartExtraLap(vehicle);
            return true;
        } else {
            return false;
        }
    }

    public boolean checkFinish(Vehicle vehicle) {
        return this.getCurrentLap(vehicle) > this.laps + 1;
    }
}
