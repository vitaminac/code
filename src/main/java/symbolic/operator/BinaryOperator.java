package symbolic.operator;

import symbolic.Expression;
import symbolic.operand.Constant;

public abstract class BinaryOperator extends Operator {
    private final Expression leftOperand;
    private final Expression rightOperand;

    protected BinaryOperator(final Expression leftOperand, final Expression rightOperand) {
        this.leftOperand = leftOperand.simplify();
        this.rightOperand = rightOperand.simplify();

    }

    @Override
    public boolean equals(Object obj) {
        if (this.getClass().equals(obj.getClass())) {
            BinaryOperator other = (BinaryOperator) obj;
            return this.getLeftOperand().equals(other.getLeftOperand()) && this.getRightOperand().equals(other.getRightOperand());
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return String.format("(%s) %s (%s)", this.getLeftOperand().toString(), this.getSymbol(), this.getRightOperand().toString());
    }

    public Expression getLeftOperand() {
        return this.leftOperand;
    }

    public Expression getRightOperand() {
        return this.rightOperand;
    }

    @Override
    protected boolean canBeSimplified() {
        return this.canMergeConstant();
    }

    protected boolean canMergeConstant() {
        return (leftOperand instanceof Constant) && (rightOperand instanceof Constant);
    }
}
