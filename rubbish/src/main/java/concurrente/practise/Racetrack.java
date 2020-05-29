package concurrente.practise;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Clase Racetrack (Tramo) encargada de modelar un tramo del circuito
 * donde correrán los coches.
 */
public class Racetrack {
    private final String name;
    private final int distance;

    /**
     * pueden ser lentos o rápidos en función de
     * si dificultan o facilitan la conducción a los pilotos,
     */
    private final int difficulty;

    public Racetrack(String name, int distance, int difficulty) {
        this.name = name;
        this.distance = distance;
        this.difficulty = difficulty;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public int getDistance() {
        return distance;
    }

    /**
     * Un tramo representa una parte de un circuito
     * y puede tener distintas formas: curvo, recto, múltiples curvas, cambio de
     * rasante, etc. cada tipo de tramo requiere de un tiempo para ser cruzado,
     * es decir, no es lo mismo un tramo recto donde los vehículos pueden ir a
     * máxima velocidad y tardarán menos tiempo en recorrerlo que uno que
     * tenga múltiples curvas.
     *
     */

    // Por el contrario,
    // tramos en línea recta serían fáciles de cubrir por los pilotos
    // y por lo tanto los vehículos irían más rápidos.
    public static Racetrack straight(String name, int distance) {
        return new Racetrack(name, distance, ThreadLocalRandom.current().nextInt(5, 7));
    }

    // tramos con muchas curvas complican la conducción
    // a los pilotos y, por tanto, ralentizan su velocidad.
    public static Racetrack curve(String name, int distance) {
        return new Racetrack(name, distance, ThreadLocalRandom.current().nextInt(0, 3));
    }

    public static Racetrack slope(String name, int distance) {
        return new Racetrack(name, distance, ThreadLocalRandom.current().nextInt(3, 5));
    }

    public static Racetrack randomCreate(String name, int distance) {
        switch (ThreadLocalRandom.current().nextInt(3)) {
            case 0:
                return curve(name + "#curva", distance);
            case 1:
                return slope(name + "#Pendiente", distance);
            default:
                return straight(name + "#Recto", distance);
        }
    }
}
