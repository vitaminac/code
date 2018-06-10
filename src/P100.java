import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

// https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&category=&problem=36
class P100 {
    private static int solve(int n) {
        int cycle = 1;
        while (n > 1) {
            if (n % 2 == 0) {
                n = n / 2;
            } else {
                n = 3 * n + 1;
            }
            ++cycle;
        }
        return cycle;
    }

    public static void main(String[] args) throws Exception {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {
                String input;
                StringTokenizer idata;
                int a, b, i, j, max, cycles;
                while (((input = br.readLine()) != null)) {
                    idata = new StringTokenizer(input);
                    a = Integer.parseInt(idata.nextToken());
                    b = Integer.parseInt(idata.nextToken());
                    max = 1;
                    if (a > b) {
                        i = b;
                        j = a;
                    } else {
                        i = a;
                        j = b;
                    }
                    for (; i <= j; i++) {
                        cycles = solve(i);
                        if (cycles > max) {
                            max = cycles;
                        }
                    }
                    writer.write(a + " " + b + " " + max + "\n");
                }
            }
        }
    }
}
