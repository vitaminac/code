package numeric;

import java.util.function.DoubleUnaryOperator;

/**
 * f'(x) = (f(x + ε) - f(x - ε)) / (2 * ε)
 */
public class NumericalDerivative {
    private final DoubleUnaryOperator function;

    public NumericalDerivative(DoubleUnaryOperator function) {
        this.function = function;
    }

    /**
     * @param x, the point we want to estimate
     *
     * @return a numerical approximation of derivative of f at point x
     */
    public double derivate(double x) {
        double epsilon = Math.pow(10, -6);
        return (this.getFunction().applyAsDouble(x + epsilon) - this.getFunction().applyAsDouble(x - epsilon)) / (2 * epsilon);
    }

    public DoubleUnaryOperator getFunction() {
        return this.function;
    }
}
