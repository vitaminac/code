package com.oop.session.figure;

public class Semicircle extends Circle {
    public Semicircle(double radius) {
        super(radius);
    }

    @Override
    public double area() {
        return super.area() / Circle.RATIO_DIAMETER;
    }
}
