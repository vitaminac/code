package code.adt;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
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
        return Arrays.asList(new Object[][]{
                {(Supplier<ArrayList<String>>) ArrayList::new},
                {(Supplier<FixedArray<String>>) () -> new FixedArray<>(5)},
                {(Supplier<BoundedStack<String>>) () -> new BoundedStack<>(5)},
                {(Supplier<LinkedList<String>>) LinkedList::new}
        });
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
}
