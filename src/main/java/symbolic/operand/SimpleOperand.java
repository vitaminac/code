package symbolic.operand;

import symbolic.Expression;
import symbolic.Factorable;

public abstract class SimpleOperand extends Expression {
    @Override
    public Expression greatestCommonFactor(Factorable other) {
        if (other instanceof SimpleOperand) {
            return super.greatestCommonFactor(other);
        } else {
            return other.greatestCommonFactor(this);
        }

    }
}
