package code.adt;

public class Rational {
    //region member
    private int numerator;
    private int denominator;
    //endregion

    //region Constructor
    public Rational(int numerator) {
        this(numerator, 1);
    }

    public Rational(int numerator, int denominator) {
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

    public void setDenominator(int denominator) {
        if (denominator == 0) {
            throw new IllegalArgumentException();
        }
        this.denominator = denominator;
    }

    //endregion
    public Rational negative() {
        return new Rational(-this.getNumerator(), this.getDenominator());
    }

    public Rational sum(Rational term) {
        int numerator = this.getNumerator() * term.getDenominator() + term.getNumerator() * this.getDenominator();
        int denominator = this.getDenominator() * term.getDenominator();
        int gcd = Math.gcd(Math.abs(numerator), Math.abs(denominator));
        return new Rational(numerator / gcd, denominator / gcd);
    }

    public Rational minus(Rational sub) {
        return this.sum(sub.negative());
    }

    public Rational multiply(Rational multiplier) {
        return new Rational(this.getNumerator() * multiplier.getNumerator(), this.getDenominator() * multiplier.getDenominator());
    }

    @Override
    public String toString() {
        return this.getNumerator() + "/" + this.getDenominator();
    }

    public double real() {
        return (double) this.getNumerator() / (double) this.getDenominator();
    }
}
