package symbolic;

import symbolic.operand.Symbol;

public interface Differentiable {
    public abstract boolean isDifferentiable();

    public abstract Expression derivative(final Symbol symbol);
}
