import algs4.fundamentals.stacks.exercises.Ex10InfixToPostfix;
import test.TestCase;
import test.TestSuite;
import test.UnitTest;

@TestSuite
public class JudgeSingleTest extends TestCase {
    @UnitTest
    public void test() throws Exception {
        Judge.judge(Ex10InfixToPostfix.class, null);
    }
}
