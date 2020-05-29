package concurrente.practise;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Panel informativo o ranking de la carrera que registra, calcula y
 * muestra información en "directo" de los vehículos de la carrera
 */
public class Score {
    public enum GameState {
        SUSPENDED, STARTED, FINISHED;
    }

    public enum CarState {
        PREPARED, RUNNING, REFUELING, EXTRAS_LAP, FINISHED
    }

    private LocalTime time;

    private final ConcurrentMap<Vehicle, CarState> states = new ConcurrentHashMap<>();
    private final ConcurrentMap<Vehicle, Integer> positionOnCurrentLap = new ConcurrentHashMap<>();
    private final ConcurrentMap<Vehicle, Integer> laps = new ConcurrentHashMap<>();

    private final ConcurrentMap<Vehicle, Integer> distanceToGoal = new ConcurrentHashMap<>();
    private final ConcurrentMap<Vehicle, Integer> rankings = new ConcurrentHashMap<>();
    private final ConcurrentMap<Vehicle, Integer> distanceToPrev = new ConcurrentHashMap<>();
    private final ConcurrentMap<Vehicle, Integer> fuels = new ConcurrentHashMap<>();

    private GameState gameState;

    // distancia a meta
    // la posicion actual,
    public void updatePosition(final Vehicle vehicle,
                               final int distanceToGoal,
                               final int position_on_current_lap) {
        this.distanceToGoal.put(vehicle, distanceToGoal);
        this.positionOnCurrentLap.put(vehicle, position_on_current_lap);
    }

    public void updateFuel(final Vehicle vehicle, int fuel) {
        this.fuels.put(vehicle, fuel);
    }

    // diferencia entre vehiculo
    public void forPass(Vehicle vehicle, int distance) {
        this.distanceToPrev.put(vehicle, distance);
    }

    public void changeRanking(final Vehicle newVehicle, final Vehicle oldVehicle) {
        synchronized (this.rankings) {
            int tmp = this.rankings.get(newVehicle);
            this.rankings.put(newVehicle, this.rankings.get(oldVehicle));
            this.rankings.put(oldVehicle, tmp);
        }
    }

    public void enterNewLap(Vehicle vehicle, int newLap) {
        this.laps.put(vehicle, newLap);
    }

    public void enterBoxForStart(Vehicle vehicle) {
        this.positionOnCurrentLap.put(vehicle, 0);
        this.laps.put(vehicle, 1);
        this.rankings.put(vehicle, this.rankings.size() + 1);
        this.distanceToPrev.put(vehicle, 0);
        this.states.put(vehicle, CarState.PREPARED);
        this.fuels.put(vehicle, vehicle.getFuel());
    }

    public void vehicleStarted(Vehicle vehicle) {
        this.states.put(vehicle, CarState.RUNNING);
    }

    public void enterBoxForFuel(Vehicle vehicle) {
        this.states.put(vehicle, CarState.REFUELING);
    }

    public void exitBoxForRunning(Vehicle vehicle) {
        this.states.put(vehicle, CarState.RUNNING);
    }

    public void vehicleStartExtraLap(Vehicle vehicle) {
        this.states.put(vehicle, CarState.EXTRAS_LAP);
    }

    public void enterBoxFinish(Vehicle vehicle) {
        this.states.put(vehicle, CarState.FINISHED);
    }

    public void notifyStart() {
        this.time = LocalTime.now();
        this.gameState = GameState.STARTED;
    }

    public void notifyFinish() {
        this.gameState = GameState.FINISHED;
    }

    public void notifySuspend() {
        this.gameState = GameState.SUSPENDED;
    }

    public void showPanel() {
        String sep = "\t\t\t";
        System.out.println("-------------------------" + " Estado de circuito = " + gameState + " " + time.until(LocalTime.now(), ChronoUnit.SECONDS) + "s -----------------------------------");
        System.out.println("Nombre\t" + sep + "Vuelta" + sep + "% de esta vuelta" + sep + "Estado\t" + sep + "Distancia para adelantar" + sep + "Distancia a meta" + sep + "Combustible" + sep + "Ranking");
        for (Vehicle vehicle : states.keySet()) {
            System.out.println(
                    vehicle + sep + laps.get(vehicle) + sep + "\t" + positionOnCurrentLap.get(vehicle) + "%" + sep + "\t\t\t\t" + states.get(vehicle)
                            + sep + String.format("%5d", distanceToPrev.get(vehicle)) + sep + "\t\t\t\t\t" + String.format("%5d", distanceToGoal.get(vehicle)) + sep + "\t\t\t" + String.format("%5d", fuels.get(vehicle)) + sep + rankings.get(vehicle)
            );
        }
    }
}
