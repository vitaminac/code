package oop.figure;

public class Semicircle extends Circle {
    public Semicircle(double radius) {
        super(radius);
    }

    @Override
    public double area() {
        return super.area() / 2;
    }
}
