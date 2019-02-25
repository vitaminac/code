package code.adt;

import java.util.Scanner;

public class StreamingAlgorithmTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int k = scanner.nextInt();
        final StreamingAlgorithm<Integer> stream = new StreamingAlgorithm<>(k);
        while (scanner.hasNext()) {
            stream.add(scanner.nextInt());
        }

        for (int i : stream.getLastKItems()) {
            System.out.println(i);
        }
    }
}
