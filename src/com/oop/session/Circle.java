package com.oop.session;

public class Circle {
    private final double m_radius;

    public Circle(double radius) {
        this.m_radius = radius;
    }

    public double circumference() {
        return 2.0 * Math.PI * this.m_radius;
    }
}
