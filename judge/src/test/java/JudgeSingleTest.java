import algs4.fundamentals.stacks.exercises.Ex4Parentheses;
import test.TestCase;
import test.TestSuite;
import test.UnitTest;

@TestSuite
public class JudgeSingleTest extends TestCase {
    @UnitTest
    public void test() throws Exception {
        Judge.judge(Ex4Parentheses.class, null);
    }
}
