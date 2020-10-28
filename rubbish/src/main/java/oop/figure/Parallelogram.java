package oop.figure;

import core.Math;
import oop.coordinates.Vector2D;

public class Parallelogram extends Figure {
    private Vector2D vectorA;
    private Vector2D vectorB;

    public Parallelogram(Vector2D vectorA, Vector2D vectorB) {
        this.vectorA = vectorA;
        this.vectorB = vectorB;
    }

    public Parallelogram(Parallelogram parallelogram) {
        this(parallelogram.getVectorA(), parallelogram.getVectorB());
    }

    public Vector2D getVectorA() {
        return this.vectorA;
    }

    public Vector2D getVectorB() {
        return this.vectorB;
    }

    @Override
    public double area() {
        return Math.abs(this.getVectorA().cross(this.getVectorB()));
    }

    @Override
    public double perimeter() {
        return (this.getVectorA().module() + this.getVectorB().module()) * 2;
    }
}
