package symbolic.operand;

import symbolic.Divisible;
import symbolic.Expression;
import symbolic.operator.arithmetic.Rational;

public abstract class SimpleOperand extends Expression {
    @Override
    public Expression greatestCommonFactor(Divisible other) {
        if (other instanceof SimpleOperand) {
            return super.greatestCommonFactor(other);
        } else {
            return other.greatestCommonFactor(this);
        }

    }

    @Override
    public Expression divide(Expression divisor) {
        return new Rational(this, divisor);
    }
}
