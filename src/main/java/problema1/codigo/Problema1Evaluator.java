package problema1.codigo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;


public class Problema1Evaluator {

    public static int[] readCase(String caseFile) {
        try (BufferedReader bf = new BufferedReader(new FileReader(caseFile))) {
            String[] tokens = bf.readLine().trim().split("\\s+");
            int[] data = new int[tokens.length];
            for (int i = 0; i < tokens.length; i++) {
                data[i] = Integer.parseInt(tokens[i]);
            }
            return data;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

	public static void main(String[] args) {
		String path = "./src/main/java/mergesort/testcases";
		int ok = 0;
		String[] inCases = new File(path).list((dir, name) -> name.endsWith(".in"));
        for (String inCase : inCases) {
            String outCase = inCase.substring(0, inCase.lastIndexOf('.')) + ".out";
            int[] inData = readCase(path+"/"+inCase);
            int[] correctData = readCase(path+"/"+outCase);

            Comparator<Integer> comp = Comparator.naturalOrder();

            Integer[] a = Arrays.stream( inData ).boxed().toArray( Integer[]::new );

            Problema1<Integer> merge = new Problema1<>(comp);
            merge.sort(a);

            int[] output = Arrays.stream(a).mapToInt(Integer::intValue).toArray();
            if (Arrays.equals(correctData, output)) {
                ok++;
            } else {
                System.out.println("ERROR EN EL CASO "+inCase);
                System.out.println("\tEsperado: " + Arrays.toString(correctData));
                System.out.println("\tObtenido: " + Arrays.toString(output));
            }

        }
        System.out.println(ok + " / " + inCases.length + " casos correctos");

    }

}
