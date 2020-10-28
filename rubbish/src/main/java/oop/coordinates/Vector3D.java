package oop.coordinates;

public class Vector3D extends Vector {
    public Vector3D(final double x, final double y, final double z) {
        super(x, y, z);
    }

    public Vector3D cross(Vector3D other) {
        // cp0 = a1 * b2 - a2 * b1
        // cp1 = a2 * b0 - a0 * b2
        // cp2 = a0 * b1 - a1 * b0
        double x = this.getCoordinateY() * other.getCoordinateZ() - this.getCoordinateZ() * other.getCoordinateY();
        double y = this.getCoordinateZ() * other.getCoordinateX() - this.getCoordinateX() * other.getCoordinateZ();
        double z = this.getCoordinateX() * other.getCoordinateY() - this.getCoordinateY() * other.getCoordinateX();
        return new Vector3D(x, y, z);
    }
}
