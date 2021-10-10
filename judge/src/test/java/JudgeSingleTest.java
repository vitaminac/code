import algs4.fundamentals.stacks.exercises.Ex11EvaluatePostfix;
import test.TestCase;
import test.TestSuite;
import test.UnitTest;

@TestSuite
public class JudgeSingleTest extends TestCase {
    @UnitTest
    public void test() throws Exception {
        Judge.judge(Ex11EvaluatePostfix.class, null);
    }
}
