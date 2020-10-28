package oop.coordinates;

public class Vector2D extends Vector {
    public Vector2D(final double x, final double y) {
        super(x, y);
    }

    public double cross(Vector2D other) {
        return this.getCoordinateX() * other.getCoordinateY() - this.getCoordinateY() * other.getCoordinateX();
    }
}
