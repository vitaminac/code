package symbolic;

import symbolic.operand.Symbol;

public interface Differentiable {
    boolean isDifferentiable();

    Expression derivative(final Symbol symbol);
}
