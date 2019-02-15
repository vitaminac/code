package oop;

import oop.coordinates.Point;
import oop.coordinates.Vector;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SessionTwo {
    @Test
    public void testPoint() {
        Point pointOne = new Point();
        Point pointTwo = new Point(2, 4);
        Vector vectorOne = new Vector(5, 6);
        Vector vectorTwo = new Vector(5, 2, 3);

        assertEquals("[0.0, 0.0]", pointOne.toString());
        assertEquals("[2.0, 4.0]", pointTwo.toString());
        assertEquals("[5.0, 6.0]", vectorOne.toString());
        assertEquals("[5.0, 2.0, 3.0]", vectorTwo.toString());

        Point pointThree = pointOne.move(vectorOne);
        assertEquals("[5.0, 6.0]", pointThree.toString());


        Point pointFour = pointTwo.move(vectorTwo);
        assertEquals("[7.0, 6.0, 3.0]", pointFour.toString());

        Point pointFive = pointThree.move(new Vector(6));
        assertEquals("[11.0, 6.0]", pointFive.toString());
        assertEquals(11.0, pointFive.getCoordinateX(), 0);

        Point pointSix = pointFour.copy();
        assertEquals("[7.0, 6.0, 3.0]", pointSix.toString());
    }
}
