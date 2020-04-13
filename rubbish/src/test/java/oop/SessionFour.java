package oop;

import oop.figure.ComplexFigure;
import oop.figure.Rectangle;
import oop.figure.Semicircle;
import oop.figure.Triangle;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SessionFour {
    @Test
    public void testFigure() {
        double radioSemicirculoIzquierdo = 60;
        double radioSemicirculoDerecho = 60;
        double alturaTriangulo = 120;
        double baseTriangulo = 100;
        double lado1Rectangulo = 90;
        double lado2Rectangulo = 95;

        ComplexFigure complexFigure = new ComplexFigure();
        complexFigure.addSection(new Semicircle(radioSemicirculoIzquierdo));
        complexFigure.addSection(new Semicircle(radioSemicirculoIzquierdo));
        complexFigure.addSection(new Triangle(baseTriangulo, alturaTriangulo));
        complexFigure.addSection(new Rectangle(lado1Rectangulo, lado2Rectangulo));

        assertEquals(25859.733552923255, complexFigure.area(), 0);
    }
}
