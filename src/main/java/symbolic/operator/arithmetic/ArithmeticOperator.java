package symbolic.operator.arithmetic;

import symbolic.Expression;
import symbolic.operator.BinaryOperator;
import symbolic.operator.property.IdentityElement;

public abstract class ArithmeticOperator extends BinaryOperator implements IdentityElement {
    protected ArithmeticOperator(final Expression leftOperand, final Expression rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected boolean canBeSimplified() {
        return super.canBeSimplified() || this.hasIdentityElement();
    }

    @Override
    public boolean hasIdentityElement() {
        return this.isIdentityElement(this.getLeftOperand()) || this.isIdentityElement(this.getRightOperand());
    }

    @Override
    public boolean isIdentityElement(final Expression operand) {
        return operand.equals(this.getIdentityElement());
    }

    @Override
    public Expression combineWithNeutralElement() {
        if (this.hasIdentityElement()) {
            if (this.isIdentityElement(this.getLeftOperand())) {
                return this.getRightOperand();
            } else {
                return this.getLeftOperand();
            }
        } else {
            return this;
        }
    }
}