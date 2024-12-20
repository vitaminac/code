import java.util.Arrays;

import core.util.Utils;
import test.*;

import static org.junit.Assert.assertTrue;

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
        int n_tests = 0;
        int n_failed = 0;
        final String[] packages = new String[]{
            "aceptaelreto",
            "algs4",
            "atcoder",
            "codechef",
            "codeforces",
            "concurso",
            "geeksforgeeks",
            "kickstart",
            "poj",
            "spoj",
            "uva",
        };
        final String[] excludedPackages = new String[]{
            "algs4.stdlib"
        };

        for (String pkg : packages) {
            for (var pair : JudgeUtils.getResources(pkg, "class")) {
                if (Arrays.stream(excludedPackages).noneMatch((excludedPackage) -> pair.packageName.startsWith(excludedPackage))) {
                    Class<?> clazz = Class.forName(
                        pair.packageName + '.' + pair.file.getName().substring(0, pair.file.getName().length() - 6));
                    try {
                        Judge.judge(clazz, null);
                        n_tests += 1;
                    } catch (Throwable e) {
                        n_failed += 1;
                        e.printStackTrace();
                    }
                } else {
                    Utils.warn("ignored " + pair.packageName + '.' + pair.file.getName().substring(0, pair.file.getName().length() - 6));
                }
            }
        }

        Utils.info("Total " + n_tests + " unit tests has run!");
        assertTrue(n_failed + " of them have failed!", n_failed <= 0);
    }
}
