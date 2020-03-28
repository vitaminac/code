package aceptaelreto;

import java.util.*;

public class P451HuyendoDeLosZombis {
    private static class Subway {
        private int subway;
        private int start;

        public Subway(int subway, int start) {
            this.subway = subway;
            this.start = start;
        }
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            while (sc.hasNextInt()) {
                int N = sc.nextInt();
                int M = sc.nextInt();
                List<Subway>[] stops = new List[N + 1];
                for (int stop = 1; stop <= N; stop++) stops[stop] = new ArrayList<>();
                sc.nextLine();
                for (int subway = 0; subway < M; subway++) {
                    StringTokenizer st = new StringTokenizer(sc.nextLine());
                    int start = 0;
                    int stop = Integer.parseInt(st.nextToken());
                    stops[stop].add(new Subway(subway, start));
                    while (st.hasMoreTokens()) {
                        start += Integer.parseInt(st.nextToken());
                        stop = Integer.parseInt(st.nextToken());
                        stops[stop].add(new Subway(subway, start));
                    }
                }
                long[][] wait = new long[M][M];
                for (long[] subway : wait) Arrays.fill(subway, Integer.MAX_VALUE);
                for (int stop = 1; stop <= N; stop++) {
                    for (Subway subwayFrom : stops[stop]) {
                        for (Subway subwayTo : stops[stop]) {
                            if (subwayTo.start < subwayFrom.start) {
                                wait[subwayFrom.subway][subwayTo.subway] = Math.min(wait[subwayFrom.subway][subwayTo.subway], subwayTo.start - subwayFrom.start + 60);
                            } else {
                                wait[subwayFrom.subway][subwayTo.subway] = Math.min(wait[subwayFrom.subway][subwayTo.subway], subwayTo.start - subwayFrom.start);
                            }
                        }
                    }
                }
                for (int k = 0; k < M; k++) {
                    for (int i = 0; i < M; i++) {
                        for (int j = 0; j < M; j++) {
                            wait[i][j] = Math.min(wait[i][j], wait[i][k] + wait[k][j]);
                        }
                    }
                }
                long min = Integer.MAX_VALUE;
                for (Subway from : stops[1]) {
                    for (Subway to : stops[N]) {
                        min = Math.min(min, wait[from.subway][to.subway]);
                    }
                }
                if (min == Integer.MAX_VALUE) {
                    System.out.println("Hoy no vuelvo");
                } else {
                    System.out.println(min);
                }
            }
        }
    }
}
