package greedy.mochila;

public class KnapsackExample {

    public static void main(String args[]) {
        Knapsack k = new Knapsack(10);
        GreedyKnapsack gk = new GreedyKnapsack(k);
        gk.greedyAlgorithmKS();
        System.out.println("Optimal soluation to knapsack instance with values given as follows");
        gk.print();
        System.out.println("=======+============+=======+============+============");
    }
}
