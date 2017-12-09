package symbolic.operator.arithmetic;

import symbolic.Divisible;
import symbolic.Expression;
import symbolic.operand.Constant;
import symbolic.operand.Symbol;

public class Multiply extends AbsorbableCommutativeArithmeticOperator {
    public Multiply(final Expression leftOperand, final Expression rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Expression getIdentityElement() {
        return Constant.ONE;
    }

    @Override
    protected String getSymbol() {
        return "*";
    }

    @Override
    public Expression getZeroElement() {
        return Constant.ZERO;
    }

    @Override
    public Constant compute(Constant leftOperand, Constant rightOperand) {
        return new Constant(leftOperand.getValue() * rightOperand.getValue());
    }

    @Override
    public Expression derivative(final Symbol symbol) {
        return new Sum(new Multiply(this.getLeftOperand().derivative(symbol), this.getRightOperand()), new Multiply(this.getLeftOperand(), this.getRightOperand().derivative(symbol))).simplify();
    }

    @Override
    public Expression substitute(final Symbol symbol, final double value) {
        return new Multiply(this.getLeftOperand().substitute(symbol, value), this.getRightOperand().substitute(symbol, value)).simplify();
    }

    @Override
    public Expression greatestCommonFactor(final Divisible other) {
        if (other instanceof Multiply) {
            return new Multiply(this.getLeftOperand().greatestCommonFactor(other), this.getRightOperand().greatestCommonFactor(other)).simplify();
        } else {
            if (this.getLeftOperand().equals(other) || this.getRightOperand().equals(other)) {
                // return other
                return other.greatestCommonFactor(other);
            }
            return Constant.ONE;
        }
    }

    @Override
    public Expression divide(Expression divisor) {
        if (this.getLeftOperand().equals(divisor)) {
            return this.getRightOperand();
        } else if (this.getRightOperand().equals(divisor)) {
            return this.getLeftOperand();
        } else {
            Expression GCFLeftOperand = this.getLeftOperand().greatestCommonFactor(divisor);
            divisor = divisor.divide(GCFLeftOperand);
            Expression GCFRightOperand = this.getRightOperand().greatestCommonFactor(divisor);
            divisor = divisor.divide(GCFRightOperand);
            return new Multiply(divisor, new Multiply(this.getLeftOperand().divide(GCFLeftOperand), this.getRightOperand().divide(GCFRightOperand)));
        }
    }

    @Override
    public Multiply commutate() {
        return new Multiply(this.getRightOperand(), this.getLeftOperand());
    }
}
