package oop.figure;

import oop.coordinates.Movable;
import oop.coordinates.Vector;

public class Circle extends Figure implements Movable {
    private final Vector origin;
    private final double radius;

    public Circle(double radius) {
        this(0, 0, radius);
    }

    public Circle(final double x, final double y, double radius) {
        this(new Vector(x, y), radius);
    }

    private Circle(final Vector origin, final double radius) {
        this.origin = origin;
        this.radius = radius;
    }

    public double getRadius() {
        return this.radius;
    }

    public Vector getOrigin() {
        return this.origin;
    }

    public double getDiameter() {
        return this.getRadius() * 2;
    }

    @Override
    public double perimeter() {
        return this.circumference();
    }

    public double circumference() {
        return Math.PI * this.getDiameter();
    }

    @Override
    public double area() {
        return Math.PI * StrictMath.pow(this.getRadius(), 2);
    }

    @Override
    public Circle move(Vector v) {
        return new Circle(this.origin.add(v), this.radius);
    }

    public boolean greater(Circle other) {
        return this.getRadius() > other.getRadius();
    }
}
