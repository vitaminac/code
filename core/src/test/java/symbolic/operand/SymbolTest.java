package symbolic.operand;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import symbolic.Expression;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class SymbolTest {
    private final double subValue = 2.5;
    private Symbol x;
    private Expression result;

    @Before
    public void setUp() throws Exception {
        this.x = new Symbol("x");
        this.result = x.substitute("x", this.subValue);

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void toStringTest() {
        assertEquals("the representation of symbol is his identity string value", "x", this.x.toString());
    }

    @Test
    public void substitute() {
        assertThat("the symbol was replaced with the constant value", result, instanceOf(Constant.class));
        assertEquals("the value of such constant is what has substituted with", String.valueOf(this.subValue), result.toString());
        assertEquals("substitute with a uninterested symbol doesnt affect to that symbol, keep untouched", "x", x.substitute("y", 2).toString());
        assertThat("substitute with a uninterested symbol doesnt affect to that symbol, keep untouched", x.substitute("y", 2), equalTo(x));
    }

    @Test
    public void derivative() {
        assertEquals("derivative with respect to the same symbol, output the identity value", String.valueOf(1.0), x.derivative("x").toString());
        assertEquals("the derivative is zero if isnt the same symbol", String.valueOf(0.0), x.derivative("y").toString());
    }

    @Test
    public void equals() {
        assertEquals(this.x, new Symbol(" x "));
    }

    @Test
    public void greatestCommonFactor() {
        assertEquals("the same symbol", this.x.greatestCommonFactor(new Symbol("x")), this.x);
        assertEquals("no commom factor, return 1", this.x.greatestCommonFactor(new Symbol("1")), new Constant(1));
    }

    @Test
    public void hashCodeTest() {
        assertEquals(this.x.hashCode(), new Symbol("   x ").hashCode());
    }
}