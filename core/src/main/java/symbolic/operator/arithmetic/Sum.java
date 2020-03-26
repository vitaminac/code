package symbolic.operator.arithmetic;

import symbolic.Expression;
import symbolic.operand.Constant;
import symbolic.operand.Symbol;
import symbolic.operator.property.CommutativeProperty;

public class Sum extends CommutativeArithmeticOperator {
    public Sum(final Expression leftOperand, final Expression rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Constant compute(Constant leftOperand, Constant rightOperand) {
        return new Constant(leftOperand.getValue() + rightOperand.getValue());
    }

    @Override
    public Expression getIdentityElement() {
        return Constant.ZERO;
    }

    @Override
    protected String getSymbol() {
        return "+";
    }

    @Override
    public Expression simplify() {
        Expression factor = this.getLeftOperand().greatestCommonFactor(this.getRightOperand());
        if (!factor.equals(Constant.ONE) && !factor.equals(Constant.ZERO)) {
            return new Multiply(factor, new Sum(this.getLeftOperand().divide(factor), this.getRightOperand().divide(factor)));
        } else {
            return super.simplify();
        }
    }

    @Override
    public Expression derivative(final Symbol symbol) {
        return new Sum(this.getLeftOperand().derivative(symbol), this.getRightOperand().derivative(symbol)).simplify();
    }

    @Override
    public Expression substitute(final Symbol symbol, final double value) {
        return new Sum(this.getLeftOperand().substitute(symbol, value), this.getRightOperand().substitute(symbol, value)).simplify();
    }

    @Override
    public Expression divide(Expression divisor) {
        return new Sum(this.getLeftOperand().divide(divisor), this.getRightOperand().divide(divisor)).simplify();
    }

    @Override
    public CommutativeProperty commutate() {
        return new Sum(this.getRightOperand(), this.getLeftOperand());
    }
}
