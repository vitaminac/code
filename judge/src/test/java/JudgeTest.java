import codeforces.P1405D;
import test.TestCase;
import test.TestSuite;
import test.UnitTest;

@TestSuite
public class JudgeTest extends TestCase {
    @UnitTest
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
            for (var pair : JudgeUtils.getResources(pkg, "class")) {
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

    @UnitTest(disabled = true)
    public void test() throws Exception {
        Judge.judge(P1405D.class, null);
    }
}
