package symbolic.operator.arithmetic;

import symbolic.Expression;
import symbolic.operator.property.ZeroElementBinaryOperation;

public abstract class AbsorbableCommutativeArithmeticOperator extends CommutativeArithmeticOperator implements ZeroElementBinaryOperation {

    public AbsorbableCommutativeArithmeticOperator(Expression leftOperand, Expression rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Expression simplify() {
        if (this.hasAbsorber()) {
            return this.absorb();
        } else {
            return super.simplify();
        }
    }

    @Override
    public boolean hasAbsorber() {
        return this.getLeftOperand().equals(this.getZeroElement()) || this.getRightOperand().equals(this.getZeroElement());
    }

    @Override
    public Expression absorb() {
        if (this.hasAbsorber()) {
            return this.getZeroElement();
        } else {
            return this;
        }
    }
}
