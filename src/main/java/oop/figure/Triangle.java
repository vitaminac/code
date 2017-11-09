package oop.figure;

import oop.coordinates.DimensionNotCoincide;
import oop.coordinates.Point;
import oop.coordinates.Vector;

public class Triangle extends Parallelogram {

    public Triangle(double base, double height) {
        super(new Vector(base, 0), new Vector(0, height));
    }

    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }


    public Triangle(Triangle triangle) {
        super(triangle);
    }


    public double getBase() {
        return this.getVectorA().module();
    }


    public double getHeight() {
        try {
            return this.getVectorB().module() * this.getVectorA().angle(this.getVectorB());
        } catch (DimensionNotCoincide e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    @Override
    public double area() {
        return super.area() / 2;
    }

    @Override
    public double perimeter() {
        return super.perimeter() + this.getVectorB().difference(this.getVectorA()).module();
    }
}
