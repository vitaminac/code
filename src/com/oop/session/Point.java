package com.oop.session;

public class Point {
    // atributos
    private double coordenadaX;
    private double coordenadaY;

    Point(double x, double y) {
        this.coordenadaX = x;
        this.coordenadaY = y;
    }

    // Constructores: reserva memoria para ese objecto
    Point() {
        this(0, 0);
    }


    public double getCoordenadaX() {
        return this.coordenadaX;
    }

    public void setCoordenadaX(double coordenadaX) {
        this.coordenadaX = coordenadaX;
    }

    public double getCoordenadaY() {
        return this.coordenadaY;
    }

    public void setCoordenadaY(double coordenadaY) {
        this.coordenadaY = coordenadaY;
    }

    // Metodo
    public void imprimePunto() {
        System.out.println("(" + this.coordenadaX + ", " + this.coordenadaY + ")");
    }

    public void transladar(double despX, double dexpY) {
        this.setCoordenadaX(this.getCoordenadaX() + despX);
        this.setCoordenadaY(this.getCoordenadaY() + dexpY);
    }

    public void transladar(double despX) {
        this.transladar(despX, 0);
    }

    public Point DameCopia() {
        Point copia = new Point(this.getCoordenadaX(), this.getCoordenadaY());
        return copia;
    }
}
