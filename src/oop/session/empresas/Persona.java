package oop.session.empresas;

public abstract class Persona {
    private String nombre;
    private int anoDeNacimiento;

    public Persona(String nombre, int anoDeNacimiento) {
        this.nombre = nombre;
        this.anoDeNacimiento = anoDeNacimiento;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int edad() {
        return 2017 - anoDeNacimiento;
    }

    public void mostrar() {
        System.out.print(
                "Nombre: " + this.getNombre() +
                        "Edad: " + this.edad()
        );
    }
}
