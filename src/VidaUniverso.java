import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

class VidaUniverso {
    public static void main(String[] args) throws Exception {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            Scanner in = new Scanner(br);
            while (in.hasNext()) {
                int num = in.nextInt();
                if (num != 42) {
                    System.out.println(num);
                }
            }
        }
    }
}
