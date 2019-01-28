package problema2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;
import java.util.Random;

public class KnapsackEvaluator {

    public static final double EPSILON = 1e-4;

    public static void main(String[] args) throws IOException {
        int nmin = 100;
        int nmax = 1000;
        Random rnd = new Random(1234);
        String path = "./src/main/java/problema2/knapsack";

        int ok = 0;

        for (int j = 0; j < 50; j++) {
            Data k = new Data(path + "/test" + (j + 1) + ".in");

            GreedyKnapsack gk = new GreedyKnapsack(k);
            gk.greedyAlgorithmKS();
            double totalProfit = Double.parseDouble(String.format(Locale.US, "%.2f", gk.getSumProfit()));

            BufferedReader bf = new BufferedReader(new FileReader(path + "/test" + (j + 1) + ".out"));
            double correctProfit = Double.parseDouble(bf.readLine().trim());
            bf.close();

            if (Math.abs(totalProfit - correctProfit) <= EPSILON) {
                ok++;
            } else {
                System.out.println("ERROR con el caso test" + (j + 1) + ".in");
                System.out.println("\tESPERADO: " + correctProfit);
                System.out.println("\tRECIBIDO: " + totalProfit);
            }
        }
        System.out.println(ok + " / 50 casos correctos");
    }
}
