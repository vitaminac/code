package code.adt;

import com.sun.prism.shader.AlphaOne_LinearGradient_AlphaTest_Loader;

import java.util.ArrayList;
import java.util.List;
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
