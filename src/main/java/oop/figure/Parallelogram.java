package oop.figure;

import oop.coordinates.Point;
import oop.coordinates.Vector;

public class Parallelogram extends Figure {
    private Vector vectorA;
    private Vector vectorB;

    public Parallelogram(Vector vectorA, Vector vectorB) {
        this.setVectorA(vectorA);
        this.setVectorB(vectorB);
    }

    public Parallelogram(Point p1, Point p2, Point p3) {
        this(p1.displacement(p2), p1.displacement(p3));
    }

    public Parallelogram(Parallelogram parallelogram) {
        this(parallelogram.getVectorA(), parallelogram.getVectorB());
    }

    public Vector getVectorA() {
        return this.vectorA;
    }

    public void setVectorA(Vector vectorA) {
        this.vectorA = vectorA;
    }

    public Vector getVectorB() {
        return this.vectorB;
    }

    public void setVectorB(Vector vectorB) {
        this.vectorB = vectorB;
    }

    @Override
    public double area() {
        return this.getVectorA()
                   .cross(this.getVectorB())
                   .module();
    }

    @Override
    public double perimeter() {
        return (this.getVectorA()
                    .module() +
                this.getVectorB()
                    .module()) * 2;
    }

}
