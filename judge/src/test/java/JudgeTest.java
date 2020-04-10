import clrs.ch2.S1InsertionSort;
import org.junit.Test;

public class JudgeTest {
    @Test
    public void judgeAll() throws Exception {
        int n_all_tests = 0;
        int n_failed = 0;
        String[] packages = new String[]{
                "aceptaelreto",
                "algs4",
                "atcoder",
                "codechef",
                "codeforces",
                "concurso",
                "kickstart",
                "poj",
                "spoj",
                "uva",
        };

        for (String pkg : packages) {
            for (var pair : Utils.getResources(pkg, "class")) {
                Class<?> clazz = Class.forName(
                        pair.packageName + '.' + pair.file.getName().substring(0, pair.file.getName().length() - 6));
                try {
                    n_all_tests += 1;
                    Judge.judge(clazz, null);
                } catch (Throwable e) {
                    n_failed += 1;
                    e.printStackTrace();
                }
            }
        }

        System.out.println(
                "Total " + n_all_tests + " unit tests and " + n_failed +
                        " of them have failed!");
    }

    @Test
    public void test() throws Exception {
        Judge.judge(S1InsertionSort.class, null);
    }
}
