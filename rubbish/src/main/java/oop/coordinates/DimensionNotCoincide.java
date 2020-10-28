package oop.coordinates;

public class DimensionNotCoincide extends IllegalArgumentException {
    public DimensionNotCoincide(int dimension1, int dimension2) {
        super(dimension1 + " not coincides with " + dimension2);
    }
}
