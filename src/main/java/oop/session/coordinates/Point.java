package oop.session.coordinates;

public class Point extends Coordinates implements Movable {

    // Constructores: reserve memory for object
    public Point(final double... coordinate) {
        super(coordinate);
    }

    public Point() {
        this(0);
    }

    public Point(double coordinateX) {
        this(coordinateX, 0);
    }

    public Point(Coordinates other) {
        super(other);
    }

    // Method

    public Point copy() {
        return new Point(this);
    }

    public Vector displacement(Point end) {
        return new Vector(this, end);
    }

    @Override
    public Point move(Vector v) {
        return new Point(this.add(v));
    }
}
