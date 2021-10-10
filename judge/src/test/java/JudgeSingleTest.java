import algs4.fundamentals.stacks.web.WebEx15Infix2Postfix;
import test.TestCase;
import test.TestSuite;
import test.UnitTest;

@TestSuite
public class JudgeSingleTest extends TestCase {
    @UnitTest
    public void test() throws Exception {
        Judge.judge(WebEx15Infix2Postfix.class, null);
    }
}
