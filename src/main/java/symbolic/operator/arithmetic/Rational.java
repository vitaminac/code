package symbolic.operator.arithmetic;

import symbolic.Expression;
import symbolic.MagicConstant;
import symbolic.operand.Constant;
import symbolic.operand.Symbol;

public class Rational extends ArithmeticOperator {
    public Rational(Expression leftOperand, Expression rightOperand) {
        super(leftOperand, rightOperand);
        if (this.getRightOperand().equals(MagicConstant.ZERO)) {
            throw new ArithmeticException("denominator is zero");
        }
    }

    @Override
    protected String getSymbol() {
        return "/";
    }

    @Override
    public Constant compute(Constant leftOperand, Constant rightOperand) {
        return new Constant(leftOperand.getValue() / rightOperand.getValue());
    }

    @Override
    public Expression simplify() {
        return super.simplify();
    }

    @Override
    public boolean isIdentityElement(Expression operand) {
        // reference check to ensure operand is at right
        return operand == this.getRightOperand() && operand.equals(this.getIdentityElement());
    }

    @Override
    public Expression getIdentityElement() {
        return MagicConstant.ONE;
    }

    @Override
    public Expression derivative(Symbol symbol) {
        return null;
    }

    @Override
    public Expression substitute(Symbol symbol, double value) {
        return new Rational(this.getLeftOperand().substitute(symbol, value), this.getRightOperand().substitute(symbol, value));
    }

    @Override
    public Expression divide(Expression divisor) {
        return new Rational(this.getLeftOperand(), new Multiply(this.getRightOperand(), divisor));
    }
}
