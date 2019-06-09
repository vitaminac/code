package code.adt.stack;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import code.adt.ArrayList;
import code.adt.BoundedStack;
import code.adt.FixedArray;
import code.adt.LinkedList;
import code.adt.Stack;

import java.util.Arrays;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class StackTest {
    private Stack<String> stack;
    private Supplier<Stack<String>> supplier;

    public StackTest(Supplier<Stack<String>> supplier) {
        this.supplier = supplier;
    }

    @Before
    public void setUp() {
        this.stack = this.supplier.get();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> initialize() {
        return Arrays.asList(new Object[][] { { (Supplier<ArrayList<String>>) ArrayList::new },
                { (Supplier<FixedArray<String>>) () -> new FixedArray<>(5) },
                { (Supplier<BoundedStack<String>>) () -> new BoundedStack<>(5) },
                { (Supplier<LinkedList<String>>) LinkedList::new } });
    }

    @Test
    public void test() {
        StringBuilder sb = new StringBuilder();
        this.stack.push("to");
        this.stack.push("be");
        this.stack.push("or");
        this.stack.push("not");
        this.stack.push("to");
        sb.append(this.stack.pop());
        sb.append(' ');
        this.stack.push("be");
        sb.append(this.stack.pop());
        sb.append(' ');
        sb.append(this.stack.pop());
        sb.append(' ');
        this.stack.push("that");
        sb.append(this.stack.pop());
        sb.append(' ');
        sb.append(this.stack.pop());
        sb.append(' ');
        sb.append(this.stack.pop());
        this.stack.push("is");
        assertEquals("to be not that or be", sb.toString());
        assertEquals(2, this.stack.size());
    }

    /**
     * Suppose that we have a sequence of intermixed push and pop operations as with
     * our test stack client, where the integers 0, 1, ..., N-1 in that order (push
     * directives) are intermixed with N minus signs (pop directives). Devise an
     * algorithm that determines whether the intermixed sequence causes the stack to
     * underflow. (You may use only an amount of space independent of N—you cannot
     * store the integers in a data structure.) Devise a linear-time algorithm that
     * determines whether a given permutation can be generated as output by our test
     * client (depending on where the pop operations occur). Solution. The stack
     * does not underflow unless there exists an integer k such that the first k pop
     * operations occur before the first k push operations.
     * 
     * If a given permutation can be generated, it is uniquely generated as follows:
     * if the next integer in the permutation is in the top of the stack, pop it;
     * otherwise, push the next integer in the input sequence onto the stack (or
     * stop if N-1 has already been pushed). The permutation can be generated if and
     * only if the stack is empty upon termination.
     */
    @Test(expected = NoSuchElementException.class)
    public void underflow() {
        this.stack.push("0");
        this.stack.push("1");
        this.stack.push("2");
        this.stack.pop();
        this.stack.pop();
        this.stack.pop();
        this.stack.pop();
    }
}
