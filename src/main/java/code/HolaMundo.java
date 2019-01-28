package main.java.code;

import java.util.Scanner;


/**
 * Imprime N veces la cadena “Hola, mundo!” en una linea por caso de prueba.
 */
class HolaMundo {
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        int num = in.nextInt();
        for (int i = 0; i < num; i++) {
            System.out.println("Hola, mundo!\n");
        }
    }
}
