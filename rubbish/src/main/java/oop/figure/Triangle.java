package oop.figure;

import oop.coordinates.DimensionNotCoincide;
import oop.coordinates.Vector2D;

public class Triangle extends Parallelogram {

    public Triangle(double base, double height) {
        super(new Vector2D(base, 0), new Vector2D(0, height));
    }


    public Triangle(Triangle triangle) {
        super(triangle);
    }


    public double getBase() {
        return this.getVectorA()
                   .module();
    }


    public double getHeight() {
        try {
            return this.getVectorB()
                       .module() *
                   this.getVectorA()
                       .angle(this.getVectorB());
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
        return super.perimeter() +
               this.getVectorB()
                   .difference(this.getVectorA())
                   .module();
    }
}
