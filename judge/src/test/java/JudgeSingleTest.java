import algs4.fundamentals.stacks.creative.CreativeProblem51EvaluateDeluxe;
import test.TestCase;
import test.TestSuite;
import test.UnitTest;

@TestSuite
public class JudgeSingleTest extends TestCase {
    @UnitTest
    public void test() throws Exception {
        Judge.judge(CreativeProblem51EvaluateDeluxe.class, null);
    }
}
