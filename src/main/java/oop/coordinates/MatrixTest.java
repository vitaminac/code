package oop.coordinates;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MatrixTest {
    private Matrix rectangularMatrixTester = new Matrix(new double[][]{{-18, 13, -4, 4}, {2, 19, -4, 12}, {-14, 11, -12, 8}, {-2, 21, 4, 8}});

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void createMatrix() {
        Exception e = null;
        try {
            this.rectangularMatrixTester.printSymmetric();

        } catch (DimensionNotCoincide dimensionNotCoincideError) {
            e = dimensionNotCoincideError;
        }
        assertEquals(e, null);
    }
}