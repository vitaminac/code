package symbolic.operator.arithmetic;

import symbolic.Expression;
import symbolic.MagicConstant;
import symbolic.operand.Constant;
import symbolic.operand.Symbol;
import symbolic.operator.property.CommutativeProperty;

public class Sum extends CommutativeArithmeticOperator {
    public Sum(final Expression leftOperand, final Expression rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Expression getIdentityElement() {
        return MagicConstant.ZERO;
    }

    @Override
    public String getSymbol() {
        return "+";
    }

    @Override
    public Expression derivative(final Symbol symbol) {
        return new Sum(this.getLeftOperand().derivative(symbol), this.getLeftOperand().derivative(symbol)).simplify();
    }

    @Override
    public Expression substitute(final Symbol symbol, final double value) {
        return new Sum(this.getLeftOperand().substitute(symbol, value), this.getRightOperand().substitute(symbol, value)).simplify();
    }

    @Override
    public Expression simplify() {
        if ((this.canBeSimplified())) {
            if (super.canBeSimplified()) {
                return new Constant(((Constant) this.getLeftOperand()).getValue() + ((Constant) this.getRightOperand()).getValue());
            } else if (this.canExtractGCF()) {
                return new Constant(1);
            } else {
                return new Multiply(new Constant(2), this.getLeftOperand());
            }
        } else {
            return this;
        }
    }

    @Override
    protected boolean canBeSimplified() {
        return super.canBeSimplified() ||
               ((this.getLeftOperand() instanceof Symbol) && (this.getRightOperand() instanceof Symbol) && ((Symbol) this.getLeftOperand()).equals(this.getRightOperand())) ||
               this.canExtractGCF();
    }

    private boolean canExtractGCF() {
        return (this.getLeftOperand() instanceof Multiply) && (this.getRightOperand() instanceof Multiply);
    }

    @Override
    public CommutativeProperty commutate() {
        return new Sum(this.getRightOperand(), this.getLeftOperand());
    }
}
