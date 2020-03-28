package aceptaelreto;

import java.util.Scanner;

public class P540PorElHuecoDeLaEscalera {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int n = sc.nextInt();
            for (int i = 0; i < n; i++) {
                int piso = sc.nextInt();
                int escalones = sc.nextInt();
                int pisoCompleto = sc.nextInt();
                int escalonesAdicional = sc.nextInt();
                int bajada = (pisoCompleto * escalones + escalonesAdicional);
                int subida = piso * escalones + bajada;
                System.out.println(bajada + " " + subida);
            }
        }
    }
}
