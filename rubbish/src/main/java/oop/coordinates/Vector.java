package oop.coordinates;

import java.util.Arrays;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoublePredicate;
import java.util.function.DoubleUnaryOperator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Vector {
    private static final DoubleUnaryOperator NEGATIVE = (c) -> -c;
    private final double[] coordinates;

    public Vector(final double... coordinate) {
        this.coordinates = coordinate;
    }

    public Vector(final Vector other) {
        this(other.coordinates);
    }

    public static Vector mean(Vector... cs) throws DimensionNotCoincide {
        int length = cs.length;
        Stream<Vector> stream = Arrays.stream(cs);
        int dimension = cs[0].getDimension();
        for (Vector c : cs) {
            if (c.getDimension() != dimension) {
                throw new DimensionNotCoincide(cs[0].getDimension(), c.getDimension());
            }
        }
        return new Vector(IntStream.range(0, dimension)
                .mapToDouble(i -> stream.mapToDouble(e -> e.coordinates[i])
                        .reduce((x, y) -> x + y)
                        .getAsDouble() / length)
                .toArray());
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
        DoubleUnaryOperator pow = (x) -> Math.pow(Math.abs(x), p);
        DoubleBinaryOperator ac = (x, y) -> x + y;
        return Math.pow(Arrays.stream(this.coordinates)
                .map(pow)
                .reduce(ac)
                .getAsDouble(), 1.0 / p);
    }

    public double euclideanNorm() {
        return this.pnorm(2);
    }

    public double module() {
        return this.euclideanNorm();
    }

    public double angle(Vector other) throws DimensionNotCoincide {
        return Math.acos(this.dot(other) / (this.module() * other.module()));
    }

    public Vector difference(Vector other) {
        return this.add(other.getOpposite());
    }

    public Vector add(Vector other) {
        if (this.getDimension() != other.getDimension())
            throw new DimensionNotCoincide(this.getDimension(), other.getDimension());

        double newCoordinates[] = new double[this.getDimension()];
        for (int i = 0; i < this.getDimension(); i++) {
            newCoordinates[i] = this.getCoordinate(i) + other.getCoordinate(i);
        }
        return new Vector(newCoordinates);
    }

    public Vector expandDimension(int dimension) {
        if (dimension < this.getDimension()) {
            throw new IllegalArgumentException();
        }
        double result[] = new double[dimension];
        System.arraycopy(this.coordinates, 0, result, 0, this.coordinates.length);
        return new Vector(result);
    }

    public double getCoordinate(int axis) {
        return this.coordinates[axis];
    }

    public double getCoordinateX() {
        return this.getCoordinate(0);
    }

    public double getCoordinateY() {
        return this.getCoordinate(1);
    }

    public double getCoordinateZ() {
        return this.getCoordinate(2);
    }

    @Override
    public String toString() {
        return Arrays.toString(this.coordinates);
    }

    public void print() {
        System.out.println(this.toString());
    }

    public int getDimension() {
        return this.coordinates.length;
    }

    public boolean isSameDimension(Vector other) {
        return this.getDimension() == other.getDimension();
    }

    public boolean isOrigin() {
        DoublePredicate p = (coordinate) -> coordinate == 0;
        return Arrays.stream(this.coordinates)
                .allMatch(p);
    }

    public Vector getOpposite() {
        return new Vector(Arrays.stream(this.coordinates)
                .map(NEGATIVE)
                .toArray());
    }
}
