package com.oop.session;

import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

public class Fraccion {
    //region member
    private int numerator;
    private int denominator;
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

    public void setDenominator(int denominator) throws ValueException {
        if (denominator == 0) {
            throw new ValueException("el denominator no puede ser cero");
        }
        this.denominator = denominator;
    }
    //endregion

    //region Constructor

    public Fraccion() {
        this(1, 1);
    }

    public Fraccion(int numerator) {
        this(numerator, 1);
    }

    public Fraccion(int numerator, int denominator) throws ValueException {
        super();
        this.setNumerator(numerator);
        this.setDenominator(denominator);
    }
    //endregion

    //region method
    public Fraccion negative() {
        return new Fraccion(-this.getNumerator(), this.getDenominator());
    }

    public Fraccion multiplicate(Fraccion multiplier) {
        return new Fraccion(this.getNumerator() * multiplier.getNumerator(), this.getDenominator() * multiplier.getDenominator());
    }

    public Fraccion sum(Fraccion terms) {
        return new Fraccion(this.getNumerator() * terms.getDenominator() + terms.getNumerator() * this.getDenominator(), this.getDenominator() * terms.getDenominator());
    }

    public Fraccion sub(Fraccion sub) {
        return this.sum(sub.negative());
    }

    public void imrime() {
        System.out.println(this.getNumerator()
                + "/"
                + this.getDenominator());
    }

    public double calculateReal() {
        return (double) this.getNumerator() / (double) this.getDenominator();
    }

    public int calculateInteger() {
        return (int) this.calculateReal();
    }
    //endregion
}
