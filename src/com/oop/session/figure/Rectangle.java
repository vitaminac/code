package com.oop.session.figure;

import com.oop.session.coordinates.DimensionNotCoincide;
import com.oop.session.coordinates.Point;
import com.oop.session.coordinates.Vector;

public class Rectangle extends Parallelogram {

    public Rectangle(Point lowerLeft, Point upperRight) {
        super(lowerLeft, lowerLeft.move(new Vector(lowerLeft.displacement(upperRight).getCoordinateX())), upperRight);
    }

    public Rectangle(double length, double width) {
        this(new Point(0, length), new Point(width, 0));
    }

    public Rectangle(Rectangle rectangle) {
        super(rectangle);
    }

    public Rectangle(Point p1, Point p2, Point p3, Point p4) {
        super(p1, p2, p3);
        Vector v12 = p1.displacement(p2);
        Vector v13 = p1.displacement(p3);
        Vector v14 = p1.displacement(p4);
        // two of this vectors are orthogonal
        try {
            if (v12.orthogonal(v13)) {
                this.setVectorA(v12);
                this.setVectorB(v13);
            } else if (v12.orthogonal(v14)) {
                this.setVectorA(v12);
                this.setVectorB(v14);
            } else if (v13.orthogonal(v14)) {
                this.setVectorA(v13);
                this.setVectorB(v14);
            } else {
                throw new IllegalArgumentException();
            }
        } catch (DimensionNotCoincide e) {
            throw new IllegalArgumentException(e);
        }
    }

    public double getLength() {
        return this.getVectorA().module();
    }

    public double getWidth() {
        return this.getVectorB().module();
    }
}
