package aceptaelreto;

import core.graph.Utils;

import java.util.*;

public class P566LaAbuelitaCaperucita {
    private static int[][] DIRS = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int T = sc.nextInt();
            for (int t = 0; t < T; t++) {
                int N = sc.nextInt();
                int M = sc.nextInt();
                // read input
                char[][] forest = new char[N][];
                for (int i = 0; i < N; i++) {
                    forest[i] = sc.next().toCharArray();
                }

                // build graph
                Map<String, Integer> weight = new HashMap<>();
                List<Integer>[] graph = new List[N * M * 2];
                for (int i = 0; i < graph.length; i++) graph[i] = new ArrayList<>();

                int source = -1;
                int sink = -1;
                for (int from_i = 0; from_i < N; from_i++) {
                    for (int from_j = 0; from_j < M; from_j++) {
                        int from = from_i * M + from_j;
                        int from_in = from * 2, from_out = from * 2 + 1;
                        if (forest[from_i][from_j] == 'C') {
                            weight.put(from_in + "-" + from_out, Integer.MAX_VALUE);
                            sink = from_out;
                        } else {
                            if (forest[from_i][from_j] == 'L') {
                                source = from_in;
                                weight.put(from_in + "-" + from_out, Integer.MAX_VALUE);
                            } else if (forest[from_i][from_j] == '.') {
                                weight.put(from_in + "-" + from_out, 1);
                            } else {
                                weight.put(from_in + "-" + from_out, 0);
                            }
                            for (int[] dir : DIRS) {
                                int to_i = from_i + dir[0];
                                int to_j = from_j + dir[1];
                                if (to_i >= 0 && to_i < N && to_j >= 0 && to_j < M) {
                                    int to = to_i * M + to_j;
                                    if (forest[to_i][to_j] == 'C' && forest[from_i][from_j] == '.') {
                                        weight.put(from_in + "-" + from_out, Integer.MAX_VALUE);
                                    }
                                    int to_in = to * 2;
                                    graph[from_out].add(to_in);
                                    weight.put(from_out + "-" + to_in, Integer.MAX_VALUE);
                                    graph[to_in].add(from_out);
                                    weight.put(to_in + "-" + from_out, 0);
                                }
                            }
                        }
                        graph[from_in].add(from_out);
                        graph[from_out].add(from_in);
                        weight.put(from_out + "-" + from_in, 0);
                    }
                }

                // Ford–Fulkerson max flow - min cut
                long flow = Utils.maxflowFordFulkerson(source, sink, graph, weight);
                if (flow >= Integer.MAX_VALUE) {
                    System.out.println("IMPOSIBLE");
                } else {
                    System.out.println(flow);
                }
            }
        }
    }
}
