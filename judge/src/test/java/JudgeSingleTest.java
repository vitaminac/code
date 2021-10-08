import algs4.fundamentals.stacks.example.ResizingArrayQueue;
import test.TestCase;
import test.TestSuite;
import test.UnitTest;

@TestSuite
public class JudgeSingleTest extends TestCase {
    @UnitTest
    public void test() throws Exception {
        Judge.judge(ResizingArrayQueue.class, null);
    }
}
