package com.oop.session;

public class Circle {
    private final double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    public double circumference() {
        return Math.PI * radius;
    }
}
