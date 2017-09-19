package com.oop.session;

public class SessionOne {
    private static final String HELLO_WORLD = "Hello, World!";

    public static void main(String[] args) {
        helloWorld();
        parOrimpar();
        Circle circulo = new Circle(12);
        System.out.println("La circunferencia de un circulo de 12 radio es " + circulo.circumference());
        System.out.println("factorial de 5 es " + factorial(3));
        System.out.println("factorial de 5 es " + factorialRecur(3));
    }

    public static void helloWorld() {
        System.out.println(HELLO_WORLD);
    }

    public static void parOrimpar() {
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                System.out.println(i + " es par");
            } else {
                System.out.println(i + " es impar");
            }
        }
    }

    public static int factorial(int number) {
        int result = 1;
        while (number > 0) {
            result *= number;
            number -= 1;
        }
        return result;
    }

    public static int factorialRecur(int number) {
        if (number == 0) {
            return 1;
        } else {
            return number * factorialRecur(number - 1);
        }
    }
}
