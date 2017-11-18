package symbolic.operand;

import symbolic.Expression;

public class Constant extends SimpleOperand {
    private final double value;

    public Constant(final double value) {
        this.value = value;
    }

    @Override
    public Expression derivative(final Symbol symbol) {
        return new Constant(0);
    }

    @Override
    public Expression substitute(final Symbol symbol, final double value) {
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Constant) {
            return this.getValue() == ((Constant) obj).getValue();
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return String.valueOf(this.getValue());
    }

    public double getValue() {
        return this.value;
    }
}
