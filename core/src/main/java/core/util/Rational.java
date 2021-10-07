package core.util;

import java.util.Objects;

import static core.util.Math.gcd;
import static core.util.Math.sign;

public class Rational {
    //region member
    private final int numerator; // prime number
    private final int denominator; // prime number
    //endregion

    //region Constructor
    public Rational(final int numerator) {
        this(numerator, 1);
    }

    public Rational(final int numerator, final int denominator) {
        if (denominator == 0) throw new IllegalArgumentException("zero denominator");
        if (numerator == 0) {
            this.numerator = 0;
            this.denominator = 1;
        } else {
            final var divisor = gcd(numerator, denominator);
            this.numerator = numerator / divisor * sign(denominator);
            this.denominator = denominator / divisor * sign(denominator);
        }
    }
    //endregion

    //region getter && setter
    public int getNumerator() {
        return numerator;
    }

    public int getDenominator() {
        return denominator;
    }

    //endregion
    public Rational negative() {
        return new Rational(-this.getNumerator(), this.getDenominator());
    }

    public Rational sum(Rational other) {
        return new Rational(
                this.getNumerator() * other.getDenominator() + other.getNumerator() * this.getDenominator(),
                this.getDenominator() * other.getDenominator());
    }

    public Rational minus(Rational sub) {
        return this.sum(sub.negative());
    }

    public Rational multiply(Rational multiplier) {
        return new Rational(
                this.getNumerator() * multiplier.getNumerator(),
                this.getDenominator() * multiplier.getDenominator());
    }

    public double getDoubleValue() {
        return (double) this.getNumerator() / (double) this.getDenominator();
    }

    @Override
    public String toString() {
        return this.getNumerator() + "/" + this.getDenominator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rational rational = (Rational) o;
        return numerator == rational.numerator &&
                denominator == rational.denominator;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numerator, denominator);
    }
}
