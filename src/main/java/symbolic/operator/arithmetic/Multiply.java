package symbolic.operator.arithmetic;

import symbolic.Expression;
import symbolic.Factorable;
import symbolic.MagicConstant;
import symbolic.operand.Constant;
import symbolic.operand.Symbol;

public class Multiply extends AbsorbableCommutativeArithmeticOperator {
    public Multiply(final Expression leftOperand, final Expression rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Expression getIdentityElement() {
        return MagicConstant.ONE;
    }

    @Override
    public String getSymbol() {
        return "*";
    }

    @Override
    public Expression getZeroElement() {
        return MagicConstant.ZERO;
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
    public Expression simplify() {
        if (super.canMergeConstant()) {
            return new Constant(((Constant) this.getLeftOperand()).getValue() * ((Constant) this.getRightOperand()).getValue());
        } else if (this.hasAbsorber()) {
            return this.absorb();
        } else if (this.hasIdentityElement()) {
            return this.combineWithNeutralElement();
        } else {
            return this;
        }
    }

    @Override
    public Expression greatestCommonFactor(final Factorable other) {
        if (other instanceof Multiply) {
            return new Multiply(this.getLeftOperand().greatestCommonFactor(other), this.getRightOperand().greatestCommonFactor(other)).simplify();
        } else {
            if (this.getLeftOperand().equals(other) || this.getRightOperand().equals(other)) {
                // return other
                return other.greatestCommonFactor(other);
            }
            return MagicConstant.ONE;
        }
    }


    @Override
    public Multiply commutate() {
        return new Multiply(this.getRightOperand(), this.getLeftOperand());
    }
}
