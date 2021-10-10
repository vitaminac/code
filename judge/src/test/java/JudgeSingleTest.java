import algs4.fundamentals.stacks.creative.CreativeProblem32Josephus;
import test.TestCase;
import test.TestSuite;
import test.UnitTest;

@TestSuite
public class JudgeSingleTest extends TestCase {
    @UnitTest
    public void test() throws Exception {
        Judge.judge(CreativeProblem32Josephus.class, null);
    }
}
