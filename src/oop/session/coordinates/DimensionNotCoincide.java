package oop.session.coordinates;

public class DimensionNotCoincide extends Exception {
    public DimensionNotCoincide(int dimension1, int dimension2) {
        super(dimension1 + " not coincides with " + dimension2);
    }
}
