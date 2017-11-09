package oop.coordinates;

import java.util.Arrays;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

public class Vector extends Coordinates {

    public Vector(final double... coordinate) {
        super(coordinate);
    }

    public Vector(final Coordinates coordinates) {
        super(coordinates);
    }

    public Vector(Point start, Point end) {
        this(end.difference(start));
    }

    public Vector(Vector other) {
        super(other);
    }

    public double dot(Vector other) throws DimensionNotCoincide {
        if (isSameDimension(other)) {
            double product = 0;
            for (int i = 0; i < this.getDimension(); i++) {
                product += this.getCoordinate(i) * other.getCoordinate(i);
            }
            return product;
        }
        throw new DimensionNotCoincide(this.getDimension(), other.getDimension());
    }

    public boolean orthogonal(Vector other) throws DimensionNotCoincide {
        return this.dot(other) == 0;
    }

    public boolean isZeroVector() {
        return this.isOrigin();
    }

    public double pnorm(int p) {
        if (p == 0) {
            throw new IllegalArgumentException();
        }
        DoubleUnaryOperator square = (x) -> Math.pow(Math.abs(x), p);
        DoubleBinaryOperator ac = (x, y) -> x + y;
        return Math.pow(Arrays.stream(this.getCoordinates())
                              .map(square)
                              .reduce(ac)
                              .getAsDouble(), 1 / p);
    }

    public double euclideanNorm() {
        return this.pnorm(2);
    }

    public double module() {
        return this.euclideanNorm();
    }


    @Override
    public Vector expandDimensionIfNecessary(int dimension) {
        return new Vector(super.expandDimensionIfNecessary(dimension));
    }

    public Vector cross(Vector other) {
        Vector v1 = this.expandDimensionIfNecessary(3);
        Vector v2 = other.expandDimensionIfNecessary(3);
        // cp0 = a1 * b2 - a2 * b1
        // cp1 = a2 * b0 - a0 * b2
        // cp2 = a0 * b1 - a1 * b0
        return new Vector(v1.getCoordinateY() * v2.getCoordinateZ() - v1.getCoordinateZ() * v2.getCoordinateY(),
                          v1.getCoordinateZ() * v2.getCoordinateX() - v1.getCoordinateX() * v2.getCoordinateZ(),
                          v1.getCoordinateX() * v2.getCoordinateY() - v1.getCoordinateY() * v2.getCoordinateX());
    }

    public double angle(Vector other) throws DimensionNotCoincide {
        return Math.acos(this.dot(other) / (this.module() * other.module()));
    }

    public Vector difference(Vector other) {
        return new Vector(super.difference(other));
    }
}
