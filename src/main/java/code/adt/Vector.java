package code.adt;

public class Vector {
    private double[] components;

    public Vector(int dimension) {
        this.components = new double[dimension];
    }

    public Vector(double... a) {
        this.components = a;
    }

    public int dimension() {
        return this.components.length;
    }

    public double dot(Vector that) {
        if (this.dimension() != that.dimension()) throw new IllegalArgumentException();
        double sum = 0.0;
        for (int i = 0; i < this.dimension(); i++)
            sum = sum + (this.components[i] * that.components[i]);
        return sum;
    }

    public double module() {
        return Math.sqrt(this.dot(this));
    }

    public double distanceTo(Vector that) {
        if (this.dimension() != that.dimension()) throw new IllegalArgumentException();
        return this.minus(that).module();
    }

    public Vector plus(Vector that) {
        this.check(that);
        Vector c = new Vector(this.dimension());
        for (int i = 0; i < this.dimension(); i++)
            c.components[i] = this.components[i] + that.components[i];
        return c;
    }

    public Vector minus(Vector that) {
        this.check(that);
        Vector c = new Vector(this.dimension());
        for (int i = 0; i < this.dimension(); i++)
            c.components[i] = this.components[i] - that.components[i];
        return c;
    }

    public double cartesian(int i) {
        return components[i];
    }

    public Vector scale(double alpha) {
        Vector c = new Vector(this.dimension());
        for (int i = 0; i < this.dimension(); i++)
            c.components[i] = alpha * components[i];
        return c;
    }

    public Vector direction() {
        if (this.module() == 0.0) throw new ArithmeticException("Zero-vector has no direction");
        return this.scale(1.0 / this.module());
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < this.dimension(); i++)
            s.append(components[i]).append(" ");
        return s.toString();
    }

    private void check(Vector that) {
        if (this.dimension() != that.dimension()) throw new IllegalArgumentException("Dimensions don't agree");
    }
}