
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.StringTokenizer;

//https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=979
class P10038 {

    static int diff(int a, int b) {
        return Math.abs(a - b);
    }

    public static void main(String[] args) throws Exception {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {
                String input;
                StringTokenizer idata;
                int before, next, dif, n, i;
                HashSet<Integer> set = new HashSet<>();
                while (((input = br.readLine()) != null)) {
                    idata = new StringTokenizer(input);
                    n = Integer.parseInt(idata.nextToken());
                    before = Integer.parseInt(idata.nextToken());
                    i = n - 1;
                    set.clear();
                    while (i > 0) {
                        next = Integer.parseInt(idata.nextToken());
                        dif = diff(before, next);
                        if (dif > 0 && dif < n && !set.contains(dif)) {
                            set.add(dif);
                            before = next;
                            i = i - 1;
                        } else {
                            break;
                        }
                    }
                    if (i == 0) {
                        writer.write("Jolly\n");
                    } else {
                        writer.write("Not jolly\n");
                    }
                }
            }
        }
    }
}
