package com.oop.session;

public class SessionTwo {
    public static void main(String[] args) {
        // write your code here
        System.out.print("Poramide...: \n");
        Piramide();

        System.out.println();

        System.out.print("Cuenta atras...: \n");
        CuentaAtras();

        Point pointUno = new Point();
        Point pointDos = new Point(2, 4);

        pointUno.imprimePunto();
        pointDos.imprimePunto();

        pointUno.transladar(5, 1);
        pointUno.imprimePunto();

        pointDos.transladar(5, 1);
        pointDos.imprimePunto();

        pointUno.transladar(6);
        pointUno.imprimePunto();

        System.out.println("La x de punto 1: " + pointUno.getCoordenadaX());
        // System.out.println("La x de punto 1: " + pointUno.coordenadaX);

        Point pointTres = pointDos.DameCopia();
        System.out.println("Copia de Point 2: ");
        pointTres.imprimePunto();
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
