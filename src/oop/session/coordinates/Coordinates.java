package oop.session.coordinates;

import java.util.Arrays;
import java.util.function.DoublePredicate;
import java.util.function.DoubleUnaryOperator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Coordinates {
    private static final DoubleUnaryOperator NEGATIVE = (c) -> -c;
    private final double coordinates[];


    public Coordinates(final double... coordinates) {
        this.coordinates = coordinates.clone();
    }

    public Coordinates(Coordinates other) {
        this(other.getCoordinates());
    }

    public Coordinates difference(Coordinates other) {
        return this.add(other.getOpposite());
    }

    public Coordinates add(Coordinates other) {
        Coordinates c1;
        Coordinates c2;
        int dimension = this.getDimension() > other.getDimension() ? this.getDimension() : other.getDimension();
        c1 = this.expandDimensionIfNecessary(dimension);
        c2 = other.expandDimensionIfNecessary(dimension);

        double newCoordinates[] = new double[c1.getDimension()];
        for (int i = 0; i < c1.getDimension(); i++) {
            newCoordinates[i] = c1.getCoordinate(i) + c2.getCoordinate(i);
        }
        return new Coordinates(newCoordinates);
    }

    public Coordinates expandDimension(int dimension) {
        if (dimension < this.getDimension()) {
            throw new IllegalArgumentException();
        }
        double result[] = new double[dimension];
        System.arraycopy(this.getCoordinates(), 0, result, 0, this.getCoordinates().length);
        return new Coordinates(result);
    }

    public Coordinates expandDimensionIfNecessary(int dimension) {
        if (dimension > this.getDimension()) {
            return expandDimension(dimension);
        } else if (dimension == this.getDimension()) {
            return this;
        } else {
            throw new IllegalArgumentException("expanded dimension must be greater then current dimension " + this.getDimension());
        }
    }

    public double[] getCoordinates() {
        return this.coordinates;
    }

    public double getCoordinate(int axis) {
        return this.getCoordinates()[axis];
    }

    public double setCoordinate(int axis, double value) {
        return this.getCoordinates()[axis] = value;
    }

    public double getCoordinateX() {
        return this.getCoordinate(0);
    }

    public void setCoordinateX(double coordinateX) {
        this.setCoordinate(0, coordinateX);
    }

    public double getCoordinateY() {
        return this.getCoordinates()[1];
    }

    public void setCoordinateY(double coordinateY) {
        this.setCoordinate(1, coordinateY);
    }

    public double getCoordinateZ() {
        return this.getCoordinates()[2];
    }

    public void setCoordinateZ(double coordinateY) {
        this.setCoordinate(2, coordinateY);
    }

    public void print() {
        System.out.println(Arrays.toString(this.getCoordinates()));
    }

    public int getDimension() {
        return this.getCoordinates().length;
    }

    public boolean isSameDimension(Coordinates other) {
        return this.getDimension() == other.getDimension();
    }

    public boolean isOrigin() {
        DoublePredicate p = (coordinate) -> coordinate == 0;
        return Arrays.stream(this.getCoordinates())
                     .allMatch(p);
    }

    public Coordinates getOpposite() {
        return new Coordinates(Arrays.stream(this.getCoordinates())
                                     .map(this.NEGATIVE)
                                     .toArray());
    }

    public static Coordinates mean(Coordinates... cs) throws DimensionNotCoincide {
        int length = cs.length;
        Stream<Coordinates> stream = Arrays.stream(cs);
        int dimension = cs[0].getDimension();
        for (Coordinates c : cs) {
            if (c.getDimension() != dimension) {
                throw new DimensionNotCoincide(cs[0].getDimension(), c.getDimension());
            }
        }
        return new Coordinates(IntStream.range(0, dimension)
                                        .mapToDouble(i -> stream.mapToDouble(e -> e.getCoordinates()[i])
                                                                .reduce((x, y) -> x + y)
                                                                .getAsDouble() / length)
                                        .toArray());
    }
}
