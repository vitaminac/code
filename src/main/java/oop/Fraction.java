package oop;

public class Fraction {
    //region member
    private int numerator;
    private int denominator;
    //endregion

    //region Constructor
    public Fraction() {
        this(1);
    }

    public Fraction(int numerator) {
        this(numerator, 1);
    }

    public Fraction(int numerator, int denominator) {
        this.setNumerator(numerator);
        this.setDenominator(denominator);
    }
    //endregion


    //region getter && setter
    public int getNumerator() {
        return numerator;
    }

    public void setNumerator(int numerator) {
        this.numerator = numerator;
    }

    public int getDenominator() {
        return denominator;
    }

    public void setDenominator(int denominator) throws IllegalArgumentException {
        if (denominator == 0) {
            throw new IllegalArgumentException("el denominator no puede ser cero");
        }
        this.denominator = denominator;
    }
    //endregion


    //region method arithmetic operation
    public Fraction negative() {
        return new Fraction(-this.getNumerator(), this.getDenominator());
    }

    public Fraction multiplicate(Fraction multiplier) {
        return new Fraction(this.getNumerator() * multiplier.getNumerator(), this.getDenominator() * multiplier.getDenominator());
    }

    public Fraction sum(Fraction terms) {
        return new Fraction(this.getNumerator() * terms.getDenominator() + terms.getNumerator() * this.getDenominator(), this.getDenominator() * terms.getDenominator());
    }

    public Fraction sub(Fraction sub) {
        return this.sum(sub.negative());
    }

    @Override
    public String toString() {
        return this.getNumerator() + "/" + this.getDenominator();
    }

    public void imrime() {
        System.out.println(this.toString());
    }

    public double decimal() {
        return (double) this.getNumerator() / (double) this.getDenominator();
    }

    public int roundToInteger() {
        return (int) this.decimal();
    }
    //endregion
}
