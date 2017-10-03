package com.oop.session;

public class Circle {
    private final double m_radius;
    private final Point m_origin;

    public Circle(double radius) {
        this(new Point(0, 0), radius);
    }

    public Circle(Point m_origin, double radius) {
        this.m_origin = m_origin;
        this.m_radius = radius;
    }

    public double circumference() {
        return 2.0 * Math.PI * this.m_radius;
    }
}
