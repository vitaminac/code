package numeric;

import org.junit.Before;
import org.junit.Test;

import java.util.function.DoubleUnaryOperator;

import static org.junit.Assert.assertEquals;

public class NumericalDerivativeTest {
    private DoubleUnaryOperator square;
    private NumericalDerivative fPrime;
    private NumericalDerivative fPrimeUsingStaticMethod;
    private NumericalDerivative fPrimeUsingNoStaticMethod;
    private NumericalDerivative fPrimeUsingAnonymousClass;

    public static double square(double x) {
        return Math.pow(x, 2.0);
    }

    public double powerByTwo(double x) {
        return Math.pow(x, 2.0);
    }

    @Before
    public void setUp() {
        this.square = x -> Math.pow(x, 2);
        // lambda
        this.fPrime = new NumericalDerivative(this.square);
        // Method Reference to static method
        this.fPrimeUsingStaticMethod = new NumericalDerivative(NumericalDerivativeTest::square);
        // Method Reference to non-static method
        this.fPrimeUsingNoStaticMethod = new NumericalDerivative(this::powerByTwo);
        // anonymous class
        this.fPrimeUsingAnonymousClass = new NumericalDerivative(new DoubleUnaryOperator() {
            @Override
            public double applyAsDouble(double operand) {
                return Math.pow(operand, 2);
            }
        });
    }

    @Test
    public void derivate() {
        double tolerance = Math.pow(10, -8);

        assertEquals(this.fPrime.derivate(5), 2 * 5, tolerance);

        assertEquals(this.fPrimeUsingStaticMethod.derivate(5), 2 * 5, tolerance);

        assertEquals(this.fPrimeUsingNoStaticMethod.derivate(5), 2 * 5, tolerance);

        assertEquals(this.fPrimeUsingAnonymousClass.derivate(5), 2 * 5, tolerance);
    }
}