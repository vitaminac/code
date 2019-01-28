package code;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

//https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=979
class P10038 {
    public static void main(String[] args) throws Exception {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String input;
            StringTokenizer tokenizer;
            int before, next, dif, n, i;
            while (((input = br.readLine()) != null)) {
                tokenizer = new StringTokenizer(input);
                n = Integer.parseInt(tokenizer.nextToken());
                boolean[] bitMap = new boolean[n];
                before = Integer.parseInt(tokenizer.nextToken());
                i = n - 1;
                while (i > 0) {
                    next = Integer.parseInt(tokenizer.nextToken());
                    dif = Math.abs(before - next);
                    if (dif > 0 && dif < n && !bitMap[dif]) {
                        bitMap[dif] = true;
                        before = next;
                        i = i - 1;
                    } else {
                        break;
                    }
                }
                if (i == 0) {
                    System.out.print("Jolly\n");
                } else {
                    System.out.print("Not jolly\n");
                }
            }
        }
    }
}
