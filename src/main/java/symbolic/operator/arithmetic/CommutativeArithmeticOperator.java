package symbolic.operator.arithmetic;

import symbolic.Expression;
import symbolic.operator.property.CommutativeProperty;

public abstract class CommutativeArithmeticOperator extends ArithmeticOperator implements CommutativeProperty {

    public CommutativeArithmeticOperator(Expression leftOperand, Expression rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof CommutativeArithmeticOperator && this.getClass().equals(other.getClass())) {
            CommutativeArithmeticOperator commutativeArithmeticOperator = (CommutativeArithmeticOperator) other;
            if ((this.getLeftOperand().equals(commutativeArithmeticOperator.getLeftOperand())) && (this.getRightOperand().equals(commutativeArithmeticOperator.getRightOperand()))) {
                return true;
            } else {
                // Commutative symbolc.property
                return ((this.getLeftOperand().equals(commutativeArithmeticOperator.getRightOperand())) && (this.getRightOperand().equals(commutativeArithmeticOperator.getLeftOperand())));
            }
        } else {
            return false;
        }
    }
}
