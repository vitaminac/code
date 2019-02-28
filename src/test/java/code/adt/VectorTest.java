package code.adt;

public class VectorTest {
    public static void main(String[] args) {
        double[] xdata = {1.0, 2.0, 3.0, 4.0};
        double[] ydata = {5.0, 2.0, 4.0, 1.0};
        Vector x = new Vector(xdata);
        Vector y = new Vector(ydata);

        System.out.println("   x       = " + x);
        System.out.println("   y       = " + y);

        Vector z = x.plus(y);
        System.out.println("   z       = " + z);

        z = z.scale(10.0);
        System.out.println(" 10z       = " + z);

        System.out.println("  |x|      = " + x.module());
        System.out.println(" <x, y>    = " + x.dot(y));
        System.out.println("dist(x, y) = " + x.distanceTo(y));
        System.out.println("dir(x)     = " + x.direction());
    }
}