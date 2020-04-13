package symbolic.operator.property;

import symbolic.Expression;

/**
 * In mathematics, an absorbing element is a special type of element of a set with respect to a binary operation on that set.
 * The result of combining an absorbing element with any element of the set is the absorbing element itself.
 */
public interface ZeroElementBinaryOperation {
    public abstract boolean hasAbsorber();

    public abstract Expression absorb();

    public abstract Expression getZeroElement();
}
