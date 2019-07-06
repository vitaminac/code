package symbolic.operator.property;

import symbolic.Expression;

public interface IdentityElement {
    public abstract Expression getIdentityElement();

    public abstract boolean hasIdentityElement();

    public abstract boolean isIdentityElement(Expression operand);

    public abstract Expression combineWithNeutralElement();
}
