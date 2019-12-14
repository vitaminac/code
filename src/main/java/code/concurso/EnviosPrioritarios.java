package code.concurso;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

public class EnviosPrioritarios {
    private static class Package {
        int id;
        static int Time = 0;

        public Package(int id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return String.valueOf(id);
        }

        static Package create() {
            return new Package(Time++);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Queue<Package> highQueue = new ArrayDeque<>();
        Queue<Package> lowQueue = new ArrayDeque<>();
        int N = scanner.nextInt();
        for (int i = 0; i < N; i++) {
            String token = scanner.next();
            if (token.equals("NOR")) {
                lowQueue.add(Package.create());
            } else if (token.equals("P2W")) {
                highQueue.add(Package.create());
            } else if (token.equals("R")) {
                int c = scanner.nextInt();
                do {
                    if (highQueue.size() > 0) {
                        System.out.print(highQueue.remove());
                    } else if (lowQueue.size() > 0) {
                        System.out.print(lowQueue.remove());
                    } else {
                        break;
                    }
                    if (--c > 0 && (highQueue.size() > 0 || lowQueue.size() > 0)) {
                        System.out.print(' ');
                    }
                } while (c > 0);
                System.out.println();
            }
        }
    }
}
