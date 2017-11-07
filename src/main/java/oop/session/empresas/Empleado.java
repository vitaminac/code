package oop.session.empresas;

public class Empleado extends Persona {
    private double sueldo;

    public Empleado(String nombre, int anoDeNacimiento, double sueldo) {
        super(nombre, anoDeNacimiento);
        this.sueldo = sueldo;
    }

    @Override
    public void mostrar() {
        super.mostrar();
        System.out.print(this.sueldo);
    }
}
