import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

class P10107 {
    public static void main(String[] args) throws Exception {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {
                String line;
                int[] numbers = new int[10000];
                int n = 0, idx;
                while ((line = br.readLine()) != null) {
                    //Todo: trim -> The numbers may have leading or trailing spaces
                    numbers[n++] = Integer.parseInt(line.trim());
                    Arrays.sort(numbers, 0, n);
                    idx = n / 2;
                    if (n % 2 == 0) {
                        writer.write((numbers[idx] + numbers[idx - 1]) / 2 + "\n");
                    } else {
                        writer.write(numbers[idx] + "\n");
                    }
                }
            }
        }
    }
}
