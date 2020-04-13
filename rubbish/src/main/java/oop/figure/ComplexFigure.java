package oop.figure;

import java.util.ArrayList;
import java.util.Arrays;

public class ComplexFigure extends Figure {
    private ArrayList<Figure> secciones;

    public ComplexFigure(final Figure... figures) {
        this.secciones = new ArrayList<Figure>(Arrays.asList(figures));
    }

    public void addSection(Figure seccion) {
        this.secciones.add(seccion);
    }

    @Override
    public double area() {
        double area = 0;
        for (Figure seccion : this.secciones) {
            area += seccion.area();
        }
        return area;
    }

    @Override
    public double perimeter() {
        return Double.NaN;
    }
}
