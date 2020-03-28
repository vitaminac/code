package uva;

import java.util.Scanner;

//https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=979
public class P10038 {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            while (scanner.hasNextInt()) {
                int n = scanner.nextInt();
                boolean valid = true;
                if (n > 0) {
                    int before = scanner.nextInt();
                    if (n > 1) {
                        boolean[] bitMap = new boolean[n];
                        for (int i = n - 1; i > 0; i--) {
                            int next = scanner.nextInt();
                            if (valid) {
                                int dif = Math.abs(before - next);
                                if (dif > 0 && dif < n && !bitMap[dif]) {
                                    bitMap[dif] = true;
                                    before = next;
                                } else {
                                    valid = false;
                                }
                            }
                        }
                    }
                }
                if (valid) {
                    System.out.println("Jolly");
                } else {
                    System.out.println("Not jolly");
                }
            }
        }
    }
}
