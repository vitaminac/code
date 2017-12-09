package symbolic.operand;

import symbolic.Expression;

import java.util.Objects;

public class Symbol extends SimpleOperand {
    private final String symbol;
    private final int hashCode;

    public Symbol(final String symbol) {
        this.symbol = symbol.trim();
        this.hashCode = Objects.hash(this.symbol);
    }

    @Override
    public Expression derivative(final Symbol symbol) {
        if (this.equals(symbol)) {
            return Constant.ONE;
        } else {
            return Constant.ZERO;
        }
    }

    public String getSymbol() {
        return this.symbol;
    }

    @Override
    public Expression substitute(final Symbol symbol, final double value) {
        if (this.equals(symbol)) {
            return new Constant(value);
        } else {
            return this;
        }
    }

    @Override
    public int hashCode() {
        return this.hashCode;
    }

    @Override
    public boolean equals(final Object obj) {
        return (obj instanceof Symbol) && this.getSymbol().equals(((Symbol) obj).getSymbol());
    }

    @Override
    public String toString() {
        return this.getSymbol();
    }
}
