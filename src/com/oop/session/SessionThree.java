package com.oop.session;

public class SessionThree {
    public static void main(String[] args) {
        Fraccion f1 = new Fraccion(2, 5);
        Fraccion f2 = new Fraccion(4, 6);
        f1.imrime();
        f2.imrime();
        Fraccion f3 = f1.multiplicate(f2);
        f3.imrime();
        Fraccion f4 = f2.sum(f3);
        f4.imrime();
        Fraccion f5 = f3.sub(f4);
        f5.imrime();

        f1.imrime();
        System.out.println(f1.decimal());
        System.out.println(f1.divide());
    }
}
