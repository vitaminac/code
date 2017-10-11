package com.oop.session;

import java.util.Arrays;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

public class test {
    public static void main(String[] args) {
        double[] array = {2, 3};
        DoubleUnaryOperator square = (x) -> Math.pow(x, 2);
        DoubleBinaryOperator ac = (x, y) -> x + y;
        double s = Arrays.stream(array).map(square).reduce(ac).getAsDouble();
        System.out.println(s);
    }
}
