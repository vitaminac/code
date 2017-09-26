package com.oop.session;

public class Circle {
    private final double m_radius;
    private final Point origin;

    public Circle(double radius) {
        this(new Point(0, 0), radius);
    }

    public Circle(Point origin, double radius) {
        this.origin = origin;
        this.m_radius = radius;
    }

    public double circumference() {
        return 2.0 * Math.PI * this.m_radius;
    }
}
