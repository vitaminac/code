package symbolic;

import symbolic.operand.Symbol;

public abstract class Expression implements Factorable {
    public Expression derivative(final String symbol) {
        return this.derivative(new Symbol(symbol));
    }

    public abstract Expression derivative(final Symbol symbol);

    public Expression substitute(final String symbol, final double value) {
        return this.substitute(new Symbol(symbol), value);
    }

    public abstract Expression substitute(final Symbol symbol, final double value);

    public void print() {
        System.out.println(this.toString());
    }

    protected boolean canBeSimplified() {
        return false;
    }

    public Expression simplify() {
        return this;
    }

    @Override
    public Expression greatestCommonFactor(Factorable other) {
        if (this.equals(other)) {
            return this;
        } else {
            return MagicConstant.ONE;
        }
    }
}
