import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class P11659 {
    public static void main(String[] args) throws Exception {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String input;
            StringTokenizer tokenizer;
            int n, a, trust;
            boolean[] reliable = new boolean[21];
            while (((input = br.readLine()) != null)) {
                tokenizer = new StringTokenizer(input);
                if (tokenizer.countTokens() == 2) {
                    n = Integer.parseInt(tokenizer.nextToken());
                    for (int i = 1; i <= n; i++) {
                        reliable[i] = true;
                    }
                    a = Integer.parseInt(tokenizer.nextToken());
                    if ((n == 0) && (a == 0)) {
                        break;
                    }
                    for (int i = 0; i < a; i++) {
                        input = br.readLine();
                        tokenizer = new StringTokenizer(input);
                        tokenizer.nextToken();
                        trust = Integer.parseInt(tokenizer.nextToken());
                        if (trust < 0) {
                            trust = -trust;
                            if (reliable[trust]) {
                                n -= 1;
                                reliable[trust] = false;
                            }
                        }
                    }
                    System.out.print(n + "\n");
                }
            }
        }
    }
}
