package oop.session.figure;

import oop.session.coordinates.Movable;
import oop.session.coordinates.Point;
import oop.session.coordinates.Vector;

public class Circle extends Figure implements Movable {
    public static final int RATIO_DIAMETER = 2;
    private Point origin;
    private double radius;

    public Circle(double radius) {
        this(new Point(0, 0), radius);
    }

    public Circle(final Point origin, double radius) {
        this.origin = origin;
        this.radius = radius;
    }

    public double getRadius() {
        return this.radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public Point getOrigin() {
        return this.origin;
    }

    public void setOrigin(Point origin) {
        this.origin = origin;
    }

    public double getDiameter() {
        return this.getRadius() * this.RATIO_DIAMETER;
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
        this.setOrigin(this.getOrigin().move(v));
        return this;
    }

    public boolean greater(Circle other) {
        return this.getRadius() > other.getRadius();
    }

}
