package main.java.code;

import java.util.Scanner;

/**
 * A lo largo de los a˜nos diferentes investigaciones han llegado a la conclusi´on de que la tierra
 * tarda 365,2524 d´ıas en dar un giro alrededor del sol, es por esto que se dice que cada 4 a˜nos
 * febrero tiene 29 d´ıas en lugar de 28 y que cada 100 a˜nos esta regla se ignora (a menos que
 * pasen 400 a˜nos en cuyo caso la regla se vuelve a aplicar), es decir, cada 4 a˜nos existe un a˜no
 * bisiesto a menos que el a˜no sea divisible entre 100 y no entre 400. Para no entrar en pol´emica
 * con calendarios anteriores, asumiremos que el a˜no dado ser´a mayor o igual a 1900.
 */
class Bisiesto {
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        int num = in.nextInt();
        for (int i = 0; i < num; i++) {
            int year = in.nextInt();
            do {
                year = (year + 4) / 4 * 4;
            } while (!((year % 4 == 0) && !((year % 100 == 0) && (year % 400 != 0))));
            System.out.println(year);
        }
    }
}
