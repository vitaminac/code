package code.algorithm.recursive;

import code.StdIn;

public class Hanoi {
    public static void moveDisc(int size, String desde, String hacia) {
        System.out.println("mover disco " + size + " de " + desde + " a " + hacia);
    }

    public static void moveTower(int altura, String origen, String destino, String intermedio) {
        if (altura > 0) {
            moveTower(altura - 1, origen, destino, intermedio);
            moveDisc(altura, origen, intermedio);
            moveTower(altura - 1, destino, origen, intermedio);
            moveDisc(altura, intermedio, destino);
            moveTower(altura - 1, origen, destino, intermedio);
        }
    }

    public static void main(String[] args) {
        moveTower(StdIn.readInt(), "left", "right", "mid");
    }
}
