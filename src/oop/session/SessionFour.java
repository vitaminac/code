package oop.session;

import oop.session.figure.ComplexFigure;
import oop.session.figure.Rectangle;
import oop.session.figure.Semicircle;
import oop.session.figure.Triangle;

public class SessionFour {
    public static void main(String[] args) {
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

        System.out.println(complexFigure.area());
    }
}
