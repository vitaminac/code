package symbolic;

import symbolic.operand.Constant;
import symbolic.operand.Symbol;

public abstract class Expression implements Divisible, Differentiable {
    @Override
    public boolean isDifferentiable() {
        return true;
    }

    public abstract Expression derivative(final Symbol symbol);

    public Expression derivative(final String symbol) {
        return this.derivative(new Symbol(symbol));
    }

    public Expression substitute(final String symbol, final double value) {
        return this.substitute(new Symbol(symbol), value);
    }

    public abstract Expression substitute(final Symbol symbol, final double value);

    public void print() {
        System.out.println(this.toString());
    }

    public Expression simplify() {
        return this;
    }

    @Override
    public Expression greatestCommonFactor(Divisible other) {
        if (this.equals(other)) {
            return this;
        } else {
            return Constant.ONE;
        }
    }
}
