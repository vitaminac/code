package code.concurso;

import java.util.Scanner;

/**
 * Eran dos tipos requetefinos
 * Eran dos tipos medios chiflaos
 * Eran dos tipos casi divinos
 * Eran dos tipos desvarataos
 * Si se encontraban en un en una esquina
 * O se encontraban en el cafe
 * Siempre se o´ıa con voz muy fina
 * El saludito de Don Jos´e
 * - Hola Don Pepito
 * - Hola Don Jose
 * - Pas´o Ud ya por casa
 * - Por su casa yo pas´e
 * - Vi´o Ud a mi abuela
 * - A su abuela yo a v´ı
 * - Adios Don Pepito
 * - Adios Don Jose.
 * Esta canci´on es Los Payasos de la Tele, a alguno os sonara... Dada la canci´on queremos saludar
 * a todas las personas como ellos hacian.
 *
 * La primera linea contiene T refiriendose al n´umero de casos de pruebas. Posteriormente vendr´an
 * T lineas conteniendo 4 strings. N nombre de la primera persona, G genero de la persona, M
 * nombre de la segunda persona, L genero de la segunda persona
 *
 * Dado los nombres y generos, imprima Hola Don/Donya (seg´un el genero, M ser´ıa Don y F ser´ıa
 * Donya) m´as el nombre de la persona
 */
public class HolaDonPepito {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        for (int i = 0; i < n; i++) {
            String name = scanner.next();
            String G = scanner.next();
            if (G.equals("F")) {
                System.out.println("Hola Donya " + name);
            } else {
                System.out.println("Hola Don " + name);
            }

            name = scanner.next();
            G = scanner.next();
            if (G.equals("F")) {
                System.out.println("Hola Donya " + name);
            } else {
                System.out.println("Hola Don " + name);
            }
        }
    }
}
