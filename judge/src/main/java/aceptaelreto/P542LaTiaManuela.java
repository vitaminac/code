package aceptaelreto;

import java.util.Scanner;

public class P542LaTiaManuela {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int T = sc.nextInt();
            int[] juguete = new int[3];
            int[] caja = new int[3];
            for (int t = 0; t < T; t++) {
                juguete[0] = sc.nextInt();
                juguete[1] = sc.nextInt();
                juguete[2] = sc.nextInt();
                caja[0] = sc.nextInt();
                caja[1] = sc.nextInt();
                caja[2] = sc.nextInt();
                System.out.println((
                        false
                                || (caja[0] > juguete[0] && ((caja[1] > juguete[1] && caja[2] > juguete[2]) || (caja[1] > juguete[2] && caja[2] > juguete[1])))
                                || (caja[0] > juguete[1] && ((caja[1] > juguete[0] && caja[2] > juguete[2]) || (caja[1] > juguete[2] && caja[2] > juguete[0])))
                                || (caja[0] > juguete[2] && ((caja[1] > juguete[0] && caja[2] > juguete[1]) || (caja[1] > juguete[1] && caja[2] > juguete[0])))
                ) ? "SIRVE" : "NO SIRVE");
            }
        }
    }
}
