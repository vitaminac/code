package oop.figure;

import oop.coordinates.Vector2D;

public class Rectangle extends Parallelogram {
    public Rectangle(final double lowerLeftX, final double lowerLeftY, final double length, final double width) {
        super(new Vector2D(lowerLeftX, length), new Vector2D(lowerLeftY, width));
    }

    public Rectangle(double length, double width) {
        this(0, 0, length, width);
    }

    public Rectangle(Rectangle rectangle) {
        super(rectangle);
    }

    public double getLength() {
        return this.getVectorA().module();
    }

    public double getWidth() {
        return this.getVectorB().module();
    }
}
