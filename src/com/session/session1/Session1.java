package com.session.session1;

public class Session1 {
    private static final String HELLO_WORLD = "Hello, World!";

    public static void main(String[] args) {
        helloWorld();
        parOrimpar();
        System.out.println("La longitud de circunferencia de" + "12.8" + " es " + calculateCir(12.8));
        System.out.println("factorial de 5 es " + factorial(3));
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

    public static double calculateCir(double ratio) {
        return ratio * Math.PI * 2;
    }

    public static int factorial(int number) {
        int result = 1;
        while (number > 0) {
            result *= number;
            number -= 1;
        }
        return result;
    }
}
