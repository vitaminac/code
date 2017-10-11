package com.oop.session.coordinates;

public class DimensionNotCoincide extends Exception {
    public DimensionNotCoincide(Coordinates c1, Coordinates c2) {
        super(c1.getDimension() + " not coincides with " + c2.getDimension());
    }
}
