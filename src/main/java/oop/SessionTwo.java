package oop;

import oop.coordinates.Vector;
import oop.coordinates.Point;

public class SessionTwo {
    public static void main(String[] args) {
        // write your code here
        System.out.print("Poramide...: \n");
        Piramide();

        System.out.println();

        System.out.print("Cuenta atras...: \n");
        CuentaAtras();

        Point pointOne = new Point();
        Point pointTwo = new Point(2, 4);

        pointOne.print();
        pointTwo.print();
        pointOne = pointOne.move(new Vector(5, 6));
        pointOne.print();

        pointTwo = pointTwo.move(new Vector(5, 2, 3));
        pointTwo.print();

        pointOne = pointOne.move(new Vector(6));
        pointOne.print();


        System.out.println("La x de punto 1: " + pointOne.getCoordinateX());
        // System.out.println("La x de punto 1: " + pointUno.coordenadaX);

        Point pointTres = pointTwo.copy();
        System.out.println("Copia de Point 2: ");
        pointTres.print();
    }

    private static void Piramide() {
        for (int idx = 1; idx < 10; idx++) {
            for (int idx2 = 1; idx2 <= idx; idx2++) {
                System.out.print(idx);
            }
            System.out.println();
        }
    }

    private static void CuentaAtras() {
        int[] array = new int[50];

        for (int i = 0; i < 50; i++) {
            array[i] = 50 - i;
        }
        for (int i = 0; i <= 49; i++) {
            System.out.println(array[i]);
        }
        System.out.println();
    }

}
