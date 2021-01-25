import core.Utils;
import test.*;

@TestSuite(runWith = ProfilerUnitTestRunner.class)
public class JudgeTest extends TestCase {
    @BeforeTestSuite
    public static void setupClass() {
        Utils.warn("--------------------before test suite-------------------------");

    }

    @AfterTestSuite
    public static void teardownClass() {
        Utils.warn("--------------------after test suite-------------------------");
    }

    @BeforeEachUnitTest
    public void setup() {
        Utils.warn("--------------------before unit test-------------------------");
    }

    @AfterEachUnitTest
    public void teardown() {
        Utils.warn("-------------------after unit test------------------------");
    }

    @UnitTest
    @UnitTestProfile
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
}
